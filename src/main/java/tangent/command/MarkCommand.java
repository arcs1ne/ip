package tangent.command;

import tangent.ui.Ui;

/** Marks one task as done and saves the resulting task list. */
public class MarkCommand extends StatusCommand {
    /** Creates a command at the supplied zero-based task index. */
    public MarkCommand(int taskIndex) {
        super(taskIndex);
    }

    @Override
    protected boolean targetStatus() {
        return true;
    }

    @Override
    protected void showConfirmation(Ui ui) {
        ui.showTaskStatusChanged(true);
    }
}
