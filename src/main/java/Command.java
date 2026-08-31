/**
 * Represents one action that can be performed in response to a user command.
 */
public abstract class Command {
    /** Performs this command using the application's collaborating objects. */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws TangentException;

    /** Returns whether executing this command should end the application. */
    public boolean isExit() {
        return false;
    }
}
