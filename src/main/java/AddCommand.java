public class AddCommand extends Command {

    private final Task t;

    public AddCommand(Task t){
        this.t = t;
    }

    @Override
    public void execute(TaskList tasks) throws TangentException {
        tasks.add(t);
    }
}
