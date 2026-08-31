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
                            updateTaskStatus(tasks, markIdx, true);
                            System.out.println(DIVIDER);
                            break;

                        case UNMARK:
                            int unmarkIdx = parser.parseTaskIndex(inputs, tasks);
                            updateTaskStatus(tasks, unmarkIdx, false);
                            System.out.println(DIVIDER);
                            break;

                        case DELETE:
                            int delIdx = parser.parseTaskIndex(inputs, tasks);
                            Task removedTask = tasks.remove(delIdx);
                            try {
                                STORAGE.save(tasks.toList());
                            } catch (TangentException e) {
                                tasks.add(delIdx, removedTask);
                                throw e;
                            }
                            System.out.println("got it! i've removed this task:");
                            System.out.println(removedTask);
                            if (tasks.isEmpty()) {
                                System.out.println("you now have no tasks in the list!");
                            } else if (tasks.size() == 1) {
                                System.out.println("you now have 1 task in the list!");
                            } else {
                                System.out.println("you now have " + tasks.size() + " tasks in the list!");
                            }
                            System.out.println(DIVIDER);
                            break;

                        case TODO:
                        case DEADLINE:
                        case EVENT:
                            if (inputs.length < 2 || inputs[1].trim().isEmpty()) {
                                System.out.println("please provide a task description!");
                                System.out.println(DIVIDER);
                            } else {
                                handleTask(parser.parseTask(inputs[1], commandTypes), tasks);
                            }
                            break;

                        case LIST:
                            if (tasks.isEmpty()) {
                                System.out.println("no tasks yet!");
                            }
                            for (int i = 0; i < tasks.size(); i++) {
                                System.out.println((i + 1) + ". " + tasks.get(i));
                            }
                            System.out.println(DIVIDER);
                            continue;

                        case BYE:
                            System.out.println("bye o/ hope to see you again soon");
                            System.out.println(DIVIDER);
                            return;

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

    /**
     * Marks a task as done or undone.
     *
     * @param tasks Task list containing the stored tasks.
     * @param taskIndex The index of the task to operate on.
     * @param isDone The current state of the task (done or undone).
     */
    private static void updateTaskStatus(TaskList tasks, int taskIndex, boolean isDone) throws TangentException {
        Task task = tasks.get(taskIndex);
        boolean wasDone = task.isDone();
        if (isDone) {
            task.markAsDone();
        }
        try {
            STORAGE.save(tasks.toList());
        } catch (TangentException e) {
            if (wasDone) {
                task.markAsDone();
            } else {
                task.markAsUndone();
            }
            throw e;
        }
        if (isDone) {
            System.out.println("i've marked it as done!");
        } else {
            System.out.println("i've marked it as undone!");
        }
    }

    /** Adds a validated task to the list, saves it, and displays the result. */
    private static void handleTask(Task task, TaskList tasks) throws TangentException {
        tasks.add(task);
        try {
            STORAGE.save(tasks.toList());
        } catch (TangentException e) {
            tasks.removeLast();
            throw e;
        }
        System.out.println("got it! you have a new task: ");
        System.out.println(task);
        if (tasks.size() == 1) {
            System.out.println("you now have 1 task in the list!");
        } else {
            System.out.println("you now have " + tasks.size() + " tasks in the list!");
        }
        System.out.println(DIVIDER);
    }

}
