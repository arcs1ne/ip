/** Removes one task from the task list and saves the result. */
public class DeleteCommand extends Command {
    private final int taskIndex;

    /** Creates a command for the supplied zero-based task index. */
    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TangentException {
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
