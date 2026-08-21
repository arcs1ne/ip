import java.util.*;
public class Tangent {
    private static final String DIVIDER = "____________________________________________________________";
    public static void main(String[] args) {
        String banner = "████████╗ █████╗ ███╗   ██╗ ██████╗ ███████╗███╗   ██╗████████╗\n"+
    "╚══██╔══╝██╔══██╗████╗  ██║██╔════╝ ██╔════╝████╗  ██║╚══██╔══╝\n"+
   "   ██║   ███████║██╔██╗ ██║██║  ███╗█████╗  ██╔██╗ ██║   ██║\n"+
   "   ██║   ██╔══██║██║╚██╗██║██║   ██║██╔══╝  ██║╚██╗██║   ██║\n"+
   "   ██║   ██║  ██║██║ ╚████║╚██████╔╝███████╗██║ ╚████║   ██║\n"+
   "   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚══════╝╚═╝  ╚═══╝   ╚═╝";
        System.out.println(DIVIDER);
        System.out.println(banner);
        System.out.println("good morning/afternoon/evening ^-^ I'm TANGENT.\nwhat do you want me to do?");
        System.out.println(DIVIDER);

        ArrayList<Task> tasks = new ArrayList<>();

        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine().trim();
                System.out.println(DIVIDER);
                if (input.isEmpty()) {
                    System.out.println("please enter a command or task description!");
                    System.out.println(DIVIDER);
                    continue;
                }
                String[] inputs = input.split(" ", 2);
                try {
                    Command command = Command.fromInput(inputs[0]);
                    switch (command) {
                        case MARK:
                            int markIdx = getTaskIndex(inputs, tasks);
                            updateTaskStatus(tasks, markIdx, true);
                            System.out.println(DIVIDER);
                            break;

                        case UNMARK:
                            int unmarkIdx = getTaskIndex(inputs, tasks);
                            updateTaskStatus(tasks, unmarkIdx, false);
                            System.out.println(DIVIDER);
                            break;

                        case DELETE:
                            int delIdx = getTaskIndex(inputs, tasks);
                            System.out.println("got it! i've removed this task:");
                            Task removedTask = tasks.remove(delIdx);
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
                                handleTask(inputs[1], tasks, command);
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
     * Returns the index of the task to work with given the user's input.
     * If the input does not contain a valid index, an exception is thrown.
     *
     * @param inputs String array containing the command and the rest of the input from the user.
     * @param tasks ArrayList of stored tasks.
     * @return Index of the task to operate on
     * @throws TangentException if user provides a non-numeric index or 1 <= index <= tasks.size() is false.
     */
    private static int getTaskIndex(String[] inputs, ArrayList<Task> tasks) throws TangentException {
        if (inputs.length < 2) {
            throw new TangentException("please provide a valid task number!");
        }
        try {
            int taskIndex = Integer.parseInt(inputs[1]) - 1;
            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                throw new TangentException("please provide a valid task number!");
            }
            return taskIndex;
        } catch (NumberFormatException  e) {
            throw new TangentException("please provide a valid task number!");
        }
    }

    /**
     * Marks a task as done or undone.
     *
     * @param tasks ArrayList of stored tasks.
     * @param taskIndex The index of the task to operate on.
     * @param isDone The current state of the task (done or undone).
     */
    private static void updateTaskStatus(ArrayList<Task> tasks, int taskIndex, boolean isDone) {
        if (isDone) {
            tasks.get(taskIndex).markAsDone();
            System.out.println("i've marked it as done!");
        } else {
            tasks.get(taskIndex).markAsUndone();
            System.out.println("i've marked it as undone!");
        }
        System.out.println(tasks.get(taskIndex));
    }

    /**
     * Adds a task of a specific type to the ArrayList of tasks.
     * If the details of the task are not in the right format, an exception is thrown.
     *
     * @param details The rest of the user's input after the command.
     * @param tasks ArrayList of stored tasks.
     * @param type Type of Task to add: ToDo, Event or Deadline
     * @throws TangentException if user's input does not match the specified format.
     *
     */
    private static void handleTask(String details, ArrayList<Task> tasks, Command type) throws TangentException {
        Task t;
        final String byMarker = " /by ";
        final String fromMarker = " /from ";
        final String toMarker = " /to ";
        String description = details.trim();

        switch (type) {
            case TODO:
                t = new ToDo(description);
                break;

            case DEADLINE:
                int byIndex = details.indexOf(byMarker);

                if (byIndex <= 0 || details.indexOf(byMarker, byIndex + byMarker.length()) != -1) {
                    throw new TangentException("please use: deadline DESCRIPTION /by TIME");
                }

                String deadlineDescription = details.substring(0, byIndex).trim();
                String by = details.substring(byIndex + byMarker.length()).trim();

                if (deadlineDescription.isEmpty() || by.isEmpty()) {
                    throw new TangentException("please use: deadline DESCRIPTION /by TIME");
                }

                t = new Deadline(deadlineDescription, by);
                break;

            case EVENT:
                int fromIndex = details.indexOf(fromMarker);
                int toIndex = details.indexOf(toMarker);

                if (fromIndex <= 0 || toIndex <= fromIndex
                        || details.indexOf(fromMarker, fromIndex + fromMarker.length()) != -1
                        || details.indexOf(toMarker, toIndex + toMarker.length()) != -1) {
                    throw new TangentException("please use: event DESCRIPTION /from START /to END");
                }

                String eventDescription = details.substring(0, fromIndex).trim();
                String from = details.substring(fromIndex + fromMarker.length(), toIndex).trim();
                String to = details.substring(toIndex + toMarker.length()).trim();

                if (eventDescription.isEmpty() || from.isEmpty() || to.isEmpty()) {
                    throw new TangentException("please use: event DESCRIPTION /from START /to END");
                }
                t = new Event(eventDescription, from, to);
                break;

            default:
                throw new TangentException("unknown task type!");
        }
        tasks.add(t);
        System.out.println("got it! you have a new task: ");
        System.out.println(t);
        if (tasks.size() == 1) {
            System.out.println("you now have 1 task in the list!");
        } else {
            System.out.println("you now have " + tasks.size() + " tasks in the list!");
        }
        System.out.println(DIVIDER);
    }
}
