import java.util.Scanner;

/** Coordinates Tangent's UI, parsing, task list, and storage collaborators. */
public class Tangent {
    private final Storage storage;
    private final Ui ui;
    private final Parser parser;

    /** Creates Tangent using the supplied path for persistent task storage. */
    public Tangent(String filePath) {
        this.storage = new Storage(filePath);
        this.ui = new Ui();
        this.parser = new Parser();
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
                    Command command = parser.parse(input, tasks);
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
