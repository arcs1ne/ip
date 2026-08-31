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
}
