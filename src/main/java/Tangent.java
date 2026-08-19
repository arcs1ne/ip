import java.util.Scanner;
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

        Task[] tasks = new Task[100];
        int numItems = 0;

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
                        int markIdx = getTaskIndex(inputs, numItems);
                        if (markIdx == -1) {
                            System.out.println("invalid input! please ensure you entered a valid task number!");
                        } else {
                            updateTaskStatus(tasks, markIdx, true);
                        }
                        System.out.println(DIVIDER);
                        break;

                    case "unmark":
                        int unmarkIdx = getTaskIndex(inputs, numItems);
                        if (unmarkIdx == -1) {
                            System.out.println("invalid input! please ensure you entered a valid task number!");
                        } else {
                            updateTaskStatus(tasks, unmarkIdx, false);
                        }
                        System.out.println(DIVIDER);
                        break;

                    case "todo":
                        ToDo td = new ToDo(inputs[1]);
                        tasks[numItems] = td;
                        numItems++;
                        System.out.println("got it! you have a new task:");
                        System.out.println(td);
                        System.out.println("this is the #" + numItems + " item in the list");
                        System.out.println(DIVIDER);
                        break;

                    case "list":
                        for (int i = 0; i < numItems; i++) {
                            System.out.println((i + 1) + ". " + tasks[i]);
                        }
                        System.out.println(DIVIDER);
                        continue;

                    case "bye":
                        System.out.println("bye o/ hope to see you again soon");
                        System.out.println(DIVIDER);
                        return;

                    default:
                        if (numItems >= tasks.length) {
                            System.out.println("Sorry, the task list is full.");
                            System.out.println(DIVIDER);
                            continue;
                        }
                        System.out.println("i've added: " + input);
                        tasks[numItems] = new Task(input);
                        numItems++;
                        System.out.println(DIVIDER);
                        break;
                }
            }
        }
    }

    private static int getTaskIndex(String[] inputs, int numItems) {
        if (inputs.length < 2) {
            return -1;
        }
        try {
            int taskIndex = Integer.parseInt(inputs[1]) - 1;
            if (0 <= taskIndex && taskIndex < numItems) {
                return taskIndex;
            }
        } catch (NumberFormatException  e) {
            return -1;
        }
        return -1;
    }

    private static void updateTaskStatus(Task[] tasks, int taskIndex, boolean isDone) {
        if (isDone) {
            tasks[taskIndex].markAsDone();
            System.out.println("i've marked it as done!");
        } else {
            tasks[taskIndex].markAsUndone();
            System.out.println("i've marked it as undone!");
        }
        System.out.println(tasks[taskIndex]);
    }
}
