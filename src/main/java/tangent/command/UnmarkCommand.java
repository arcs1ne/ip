package tangent.command;

import tangent.exception.TangentException;
import tangent.storage.Storage;
import tangent.task.Task;
import tangent.task.TaskList;
import tangent.ui.Ui;

/** Marks one task as incomplete and saves the resulting task list. */
public class UnmarkCommand extends Command {
    private final int taskIndex;

    /** Creates a command for the supplied zero-based task index. */
    public UnmarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TangentException {
        tasks.validateIndex(taskIndex);
        Task task = tasks.get(taskIndex);
        boolean wasDone = task.isDone();
        task.markAsUndone();
        try {
            storage.save(tasks.toList());
        } catch (TangentException e) {
            if (wasDone) {
                task.markAsDone();
            } else {
                task.markAsUndone();
            }
            throw e;
        }
        ui.showTaskStatusChanged(false);
    }
}
