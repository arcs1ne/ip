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

        String[] tasks = new String[100];
        int numItems = 0;
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine();
                if (command.equals("list")) {
                    for (int i = 0; i < numItems; i++) {
                        System.out.println((i + 1) + ". " + tasks[i]);
                    }
                    System.out.println(DIVIDER);
                    continue;
                }

                if (command.equals("bye")) {
                    System.out.println("bye o/ hope to see you again soon");
                    System.out.println(DIVIDER);
                    return;
                }
                if (numItems >= tasks.length) {
                    System.out.println("Sorry, the task list is full.");
                    System.out.println(DIVIDER);
                    continue;
                }
                System.out.println("i've added: " + command);
                tasks[numItems] = command;
                numItems++;
                System.out.println(DIVIDER);
            }
        }
    }
}
