public class AddCommand extends Command {

    private final Task t;

    public AddCommand(Task t){
        this.t = t;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TangentException {
        tasks.add(t);
    }
}
