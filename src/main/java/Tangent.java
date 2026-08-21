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
                String command = inputs[0];
                switch (command) {
                    case "mark":
                        int markIdx = getTaskIndex(inputs, tasks);
                        if (markIdx == -1) {
                            System.out.println("invalid input! please ensure you entered a valid task number!");
                        } else {
                            updateTaskStatus(tasks, markIdx, true);
                        }
                        System.out.println(DIVIDER);
                        break;

                    case "unmark":
                        int unmarkIdx = getTaskIndex(inputs, tasks);
                        if (unmarkIdx == -1) {
                            System.out.println("invalid input! please ensure you entered a valid task number!");
                        } else {
                            updateTaskStatus(tasks, unmarkIdx, false);
                        }
                        System.out.println(DIVIDER);
                        break;

                    case "todo":
                    case "deadline":
                    case "event":
                        if (inputs.length < 2 || inputs[1].trim().isEmpty()) {
                            System.out.println("please provide a task description!");
                            System.out.println(DIVIDER);
                        } else {
                            handleTask(inputs[1], tasks, command);
                        }
                        break;

                    case "list":
                        if (tasks.isEmpty()) {
                            System.out.println("no tasks yet!");
                        }
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println((i + 1) + ". " + tasks.get(i));
                        }
                        System.out.println(DIVIDER);
                        continue;

                    case "bye":
                        System.out.println("bye o/ hope to see you again soon");
                        System.out.println(DIVIDER);
                        return;

                    default:
                        System.out.println("invalid command!");
                        System.out.println(DIVIDER);
                        break;
                }
            }
        }
    }

    private static int getTaskIndex(String[] inputs, ArrayList<Task> tasks) {
        if (inputs.length < 2) {
            return -1;
        }
        try {
            int taskIndex = Integer.parseInt(inputs[1]) - 1;
            if (0 <= taskIndex && taskIndex < tasks.size()) {
                return taskIndex;
            }
        } catch (NumberFormatException  e) {
            return -1;
        }
        return -1;
    }

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

    private static void handleTask(String details, ArrayList<Task> tasks, String type) {
        Task t;
        final String byMarker = " /by ";
        final String fromMarker = " /from ";
        final String toMarker = " /to ";
        String description = details.trim();

        switch (type) {
            case "todo":
                t = new ToDo(description);
                break;

            case "deadline":
                int byIndex = details.indexOf(byMarker);

                if (byIndex <= 0 || details.indexOf(byMarker, byIndex + byMarker.length()) != -1) {
                    System.out.println("please use: deadline DESCRIPTION /by TIME");
                    System.out.println(DIVIDER);
                    return;
                }

                String deadlineDescription = details.substring(0, byIndex).trim();
                String by = details.substring(byIndex + byMarker.length()).trim();

                if (deadlineDescription.isEmpty() || by.isEmpty()) {
                    System.out.println("please use: deadline DESCRIPTION /by TIME");
                    System.out.println(DIVIDER);
                    return;
                }

                t = new Deadline(deadlineDescription, by);
                break;

            case "event":
                int fromIndex = details.indexOf(fromMarker);
                int toIndex = details.indexOf(toMarker);

                if (fromIndex <= 0 || toIndex <= fromIndex
                        || details.indexOf(fromMarker, fromIndex + fromMarker.length()) != -1
                        || details.indexOf(toMarker, toIndex + toMarker.length()) != -1) {
                    System.out.println("please use: event DESCRIPTION /from START /to END");
                    System.out.println(DIVIDER);
                    return;
                }

                String eventDescription = details.substring(0, fromIndex).trim();
                String from = details.substring(fromIndex + fromMarker.length(), toIndex).trim();
                String to = details.substring(toIndex + toMarker.length()).trim();

                if (eventDescription.isEmpty() || from.isEmpty() || to.isEmpty()) {
                    System.out.println("please use: event DESCRIPTION /from START /to END");
                    System.out.println(DIVIDER);
                    return;
                }
                t = new Event(eventDescription, from, to);
                break;

            default:
                System.out.println("unknown task type!");
                System.out.println(DIVIDER);
                return;
        }
        tasks.add(t);
        System.out.println("got it! you have a new task: ");
        System.out.println(t);
        if (tasks.size() == 1) {
            System.out.println("you now have 1 task!");
        } else {
            System.out.println("you now have " + tasks.size() + " tasks!");
        }
        System.out.println(DIVIDER);
    }
}
