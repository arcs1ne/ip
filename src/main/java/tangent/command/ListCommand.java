package tangent.command;

import tangent.storage.Storage;
import tangent.task.TaskList;
import tangent.ui.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTaskList(tasks);
    }
}
