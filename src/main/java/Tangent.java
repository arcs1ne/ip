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
                        handleTask(inputs[1], tasks, "todo");
                        break;

                    case "deadline":
                        handleTask(inputs[1], tasks, "deadline");
                        break;

                    case "event":
                        handleTask(inputs[1], tasks, "event");
                        break;

                    case "list":
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
                        System.out.println("i've added: " + input);
                        tasks.add(new Task(input));
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

    private static ArrayList<Task> handleTask(String details, ArrayList<Task> tasks, String type) {
        Task t;
        switch (type) {
            case "todo":
                t = new ToDo(details);
                break;
            case "deadline":
                String[] deadlineInputs = details.split(" /by ");
                t = new Deadline(deadlineInputs[0], deadlineInputs[1]);
                break;
            case "event":
                String[] eventInputs = details.split(" /from ");
                String[] fromTo = eventInputs[1].split(" /to ");
                t = new Event(eventInputs[0], fromTo[0], fromTo[1]);
                break;
            default:
                t = null;
        }
        tasks.add(t);
        System.out.println("got it! you have a new task: ");
        System.out.println(t);
        System.out.println("you now have " + tasks.size() + " tasks!");
        System.out.println(DIVIDER);
        return tasks;
    }
}
