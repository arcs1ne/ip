package tangent;

import tangent.command.Command;
import tangent.exception.TangentException;
import tangent.parser.Parser;
import tangent.storage.Storage;
import tangent.task.TaskList;
import tangent.ui.Ui;

import java.util.Scanner;

/** Coordinates tangent.Tangent's UI, parsing, task list, and storage collaborators. */
public class Tangent {
    private final Storage storage;
    private final Ui ui;

    /** Creates tangent.Tangent using the supplied path for persistent task storage. */
    public Tangent(String filePath) {
        this.storage = new Storage(filePath);
        this.ui = new Ui();
    }

    /** Runs tangent.Tangent's console command loop. */
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

    /** Starts tangent.Tangent with its default task data file. */
    public static void main(String[] args) {
        new Tangent("data/tangent.txt").run();
    }
}
