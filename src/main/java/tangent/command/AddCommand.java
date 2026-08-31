package tangent.command;

import tangent.exception.TangentException;
import tangent.storage.Storage;
import tangent.task.Task;
import tangent.task.TaskList;
import tangent.ui.Ui;

/** Adds one validated task to the task list and saves the result. */
public class AddCommand extends Command {

    private final Task task;

    /** Creates a command that adds the supplied task. */
    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TangentException {
        tasks.add(task);
        try {
            storage.save(tasks.toList());
        } catch (TangentException e) {
            tasks.removeLast();
            throw e;
        }
        ui.showTaskAdded(task, tasks);
    }
}
