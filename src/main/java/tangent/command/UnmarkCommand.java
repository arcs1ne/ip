package tangent.command;

import tangent.ui.Ui;

/** Marks one task as undone and saves the resulting task list. */
public class UnmarkCommand extends StatusCommand {
    /** Creates a command for the supplied zero-based task index. */
    public UnmarkCommand(int taskIndex) {
        super(taskIndex);
    }

    @Override
    protected boolean targetStatus() {
        return false;
    }

    @Override
    protected void showConfirmation(Ui ui) {
        ui.showTaskStatusChanged(false);
    }
}
