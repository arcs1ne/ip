package tangent.command;

import tangent.exception.TangentException;
import tangent.storage.Storage;
import tangent.task.Task;
import tangent.task.TaskList;
import tangent.ui.Ui;

/** Marks one task as undone and saves the resulting task list. */
public class UnmarkCommand extends Command {
    /** 0-based index of the task to be marked as undone. */
    private final int taskIndex;

    /** Creates a command for the supplied zero-based task index. */
    public UnmarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * Marks a task from {@code tasks} at the specified index as undone and persists the change in {@code storage}.
     *
     * @throws TangentException if the data file cannot be written to, or the task index is invalid.
     */
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
