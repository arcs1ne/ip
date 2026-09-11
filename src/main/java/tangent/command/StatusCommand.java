package tangent.command;

import tangent.exception.TangentException;
import tangent.storage.Storage;
import tangent.task.Task;
import tangent.task.TaskList;
import tangent.ui.Ui;

/** Changes a task's completion status and saves the resulting task list. */
public abstract class StatusCommand extends Command {
    /** 0-based index of the task whose status is changed. */
    private final int taskIndex;

    /** Creates a status command for the supplied 0-based task index. */
    protected StatusCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /** Returns the completion status this command is expected to apply. */
    protected abstract boolean targetStatus();

    /** Displays the confirmation after a successful status change. */
    protected abstract void showConfirmation(Ui ui);

    /**
     * Changes a task's status and saves the change in the storage.
     *
     * @throws TangentException if the task index is invalid or saving fails
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TangentException {
        tasks.validateIndex(taskIndex);
        Task task = tasks.get(taskIndex);
        boolean previousStatus = task.isDone();
        setStatus(task, targetStatus());
        try {
            storage.save(tasks.toList());
        } catch (TangentException e) {
            setStatus(task, previousStatus);
            throw e;
        }
        showConfirmation(ui);
    }

    /** Applies a completion status to a task. */
    private void setStatus(Task task, boolean isDone) {
        if (isDone) {
            task.markAsDone();
        } else {
            task.markAsUndone();
        }
    }
}
