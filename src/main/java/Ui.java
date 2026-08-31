import java.util.Scanner;

/**
 * Handles console input and shared messages shown to the user.
 */
public class Ui {
    private static final String DIVIDER = "____________________________________________________________";
    private static final String BANNER = """
            ████████╗ █████╗ ███╗   ██╗ ██████╗ ███████╗███╗   ██╗████████╗
            ╚══██╔══╝██╔══██╗████╗  ██║██╔════╝ ██╔════╝████╗  ██║╚══██╔══╝
               ██║   ███████║██╔██╗ ██║██║  ███╗█████╗  ██╔██╗ ██║   ██║
               ██║   ██╔══██║██║╚██╗██║██║   ██║██╔══╝  ██║╚██╗██║   ██║
               ██║   ██║  ██║██║ ╚████║╚██████╔╝███████╗██║ ╚████║   ██║
               ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚══════╝╚═╝  ╚═══╝   ╚═╝""";

    /** Displays the greeting shown when Tangent starts. */
    public void showWelcome() {
        showDivider();
        System.out.println(BANNER);
        System.out.println("good morning/afternoon/evening ^-^ I'm TANGENT.\nwhat do you want me to do?");
        showDivider();
    }

    /** Displays the divider used to separate responses in the console. */
    public void showDivider() {
        System.out.println(DIVIDER);
    }

    /** Reads and trims one command entered by the user. */
    public String readCommand(Scanner scanner) {
        return scanner.nextLine().trim();
    }

    /** Displays the tasks currently stored in the task list. */
    public void showTaskList(TaskList tasks) {
        if (tasks.isEmpty()) {
            System.out.println("no tasks yet!");
        }
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    /** Displays the farewell message shown when Tangent exits. */
    public void showGoodbye() {
        System.out.println("bye o/ hope to see you again soon");
    }
}
