public abstract class Command {
    public abstract void execute(TaskList tasks) throws TangentException;

    public boolean isExit() {
        return false;
    }
}
