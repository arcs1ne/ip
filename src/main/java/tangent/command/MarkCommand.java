package tangent.command;

import tangent.exception.TangentException;
import tangent.storage.Storage;
import tangent.task.Task;
import tangent.task.TaskList;
import tangent.ui.Ui;

/** Marks one task as complete and saves the resulting task list. */
public class MarkCommand extends Command {
    private final int taskIndex;

    /** Creates a command for the supplied zero-based task index. */
    public MarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TangentException {
        tasks.validateIndex(taskIndex);
        updateTaskStatus(tasks, true, storage);
        ui.showTaskStatusChanged(true);
    }

    /** Updates the status and restores the original status if saving fails. */
    private void updateTaskStatus(TaskList tasks, boolean isDone, Storage storage) throws TangentException {
        Task task = tasks.get(taskIndex);
        boolean wasDone = task.isDone();
        task.markAsDone();
        try {
            storage.save(tasks.toList());
        } catch (TangentException e) {
            restoreStatus(task, wasDone);
            throw e;
        }
    }

    /** Restores the supplied task's completion state. */
    private void restoreStatus(Task task, boolean wasDone) {
        if (wasDone) {
            task.markAsDone();
        } else {
            task.markAsUndone();
        }
    }
}
