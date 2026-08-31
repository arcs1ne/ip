import java.util.Scanner;

public class Tangent {
    private static final Storage STORAGE = new Storage("data/tangent.txt");
    private static final String DIVIDER = "____________________________________________________________";

    public static void main(String[] args) {
        Ui ui = new Ui();
        Parser parser = new Parser();
        ui.showWelcome();

        TaskList tasks;
        try {
            tasks = new TaskList(STORAGE.load());
        } catch (TangentException e) {
            System.out.println(e.getMessage());
            return;
        }
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String input = ui.readCommand(scanner);
                System.out.println(DIVIDER);
                if (input.isEmpty()) {
                    System.out.println("please enter a command or task description!");
                    System.out.println(DIVIDER);
                    continue;
                }
                String[] inputs = input.split(" ", 2);
                try {
                    CommandTypes commandTypes = parser.parseCommandType(inputs[0]);
                    switch (commandTypes) {
                        case MARK:
                            int markIdx = parser.parseTaskIndex(inputs, tasks);
                            Command markCommand = new MarkCommand(markIdx);
                            markCommand.execute(tasks, ui, STORAGE);
                            System.out.println(DIVIDER);
                            break;

                        case UNMARK:
                            int unmarkIdx = parser.parseTaskIndex(inputs, tasks);
                            Command unmarkCommand = new UnmarkCommand(unmarkIdx);
                            unmarkCommand.execute(tasks, ui, STORAGE);
                            System.out.println(DIVIDER);
                            break;

                        case DELETE:
                            int delIdx = parser.parseTaskIndex(inputs, tasks);
                            Command deleteCommand = new DeleteCommand(delIdx);
                            deleteCommand.execute(tasks, ui, STORAGE);
                            System.out.println(DIVIDER);
                            break;

                        case TODO:
                        case DEADLINE:
                        case EVENT:
                            if (inputs.length < 2 || inputs[1].trim().isEmpty()) {
                                System.out.println("please provide a task description!");
                                System.out.println(DIVIDER);
                            } else {
                                Command addCommand = new AddCommand(parser.parseTask(inputs[1], commandTypes));
                                addCommand.execute(tasks, ui, STORAGE);
                                System.out.println(DIVIDER);
                            }
                            break;

                        case LIST:
                            Command listCommand = new ListCommand();
                            listCommand.execute(tasks, ui, STORAGE);
                            System.out.println(DIVIDER);
                            continue;

                        case BYE:
                            Command exitCommand = new ExitCommand();
                            exitCommand.execute(tasks, ui, STORAGE);
                            System.out.println(DIVIDER);
                            if (exitCommand.isExit()) {
                                return;
                            }
                            break;

                        default:
                            System.out.println("invalid command!");
                            System.out.println(DIVIDER);
                            break;
                    }
                } catch (TangentException e) {
                    System.out.println(e.getMessage());
                    System.out.println(DIVIDER);
                }
            }
        }
    }

}
