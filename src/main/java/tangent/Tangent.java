package tangent;

import java.util.Scanner;

import tangent.command.Command;
import tangent.exception.TangentException;
import tangent.parser.Parser;
import tangent.storage.Storage;
import tangent.task.TaskList;
import tangent.ui.Ui;

/** Coordinates Tangent's UI, parsing, task list, and storage. */
public class Tangent {
    /** A storage object that handles all interactions with the data file. */
    private final Storage storage;
    /** A user interface object that handles all console responses. */
    private final Ui ui;

    /** Creates a new instance of Tangent using the supplied file path. */
    public Tangent(String filePath) {
        this.storage = new Storage(filePath);
        this.ui = new Ui();
    }

    /** Runs Tangent's console command loop. */
    public void run() {
        ui.showWelcome();
        TaskList tasks;
        try {
            tasks = new TaskList(storage.load());
        } catch (TangentException e) {
            ui.showError(e.getMessage());
            return;
        }
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String input = ui.readCommand(scanner);
                ui.showDivider();
                if (input.isEmpty()) {
                    ui.showError("please enter a command or task description!");
                    ui.showDivider();
                    continue;
                }
                try {
                    Command command = Parser.parse(input);
                    command.execute(tasks, ui, storage);
                    ui.showDivider();
                    if (command.isExit()) {
                        return;
                    }
                } catch (TangentException e) {
                    ui.showError(e.getMessage());
                    ui.showDivider();
                }
            }
        }
    }

    /** Starts Tangent with its default task data file. */
    public static void main(String[] args) {
        new Tangent("data/tangent.txt").run();
    }
}
