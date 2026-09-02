package tangent.command;
import tangent.exception.TangentException;
import tangent.storage.Storage;
import tangent.task.Task;
import tangent.task.TaskList;
import tangent.ui.Ui;

/** Removes one task from the task list and saves the result. */
public class DeleteCommand extends Command {

    /** 0-based index of the task to be removed. */
    private final int taskIndex;

    /**
     * Creates a command that deletes the task at the specified 0-based index.
     */
    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * Deletes a task from {@code tasks} at the specified index and persists the removal in {@code storage}.
     *
     * @throws TangentException if the data file cannot be written to, or the task index is invalid.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TangentException {
        tasks.validateIndex(taskIndex);
        Task removedTask = tasks.remove(taskIndex);
        try {
            storage.save(tasks.toList());
        } catch (TangentException e) {
            tasks.add(taskIndex, removedTask);
            throw e;
        }
        ui.showTaskDeleted(removedTask, tasks);
    }
}
