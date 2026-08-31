package tangent.ui;

import tangent.task.Task;
import tangent.task.TaskList;

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

    /** Displays the greeting shown when tangent.Tangent starts. */
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

    /** Displays an error message from the application. */
    public void showError(String message) {
        System.out.println(message);
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

    /** Displays the farewell message shown when tangent.Tangent exits. */
    public void showGoodbye() {
        System.out.println("bye o/ hope to see you again soon");
    }

    /** Displays confirmation that a task's completion status has changed. */
    public void showTaskStatusChanged(boolean isDone) {
        if (isDone) {
            System.out.println("i've marked it as done!");
        } else {
            System.out.println("i've marked it as undone!");
        }
    }

    /** Displays confirmation that a task was removed and the remaining task count. */
    public void showTaskDeleted(Task removedTask, TaskList tasks) {
        System.out.println("got it! i've removed this task:");
        System.out.println(removedTask);
        if (tasks.isEmpty()) {
            System.out.println("you now have no tasks in the list!");
        } else if (tasks.size() == 1) {
            System.out.println("you now have 1 task in the list!");
        } else {
            System.out.println("you now have " + tasks.size() + " tasks in the list!");
        }
    }

    /** Displays confirmation that a task was added and the current task count. */
    public void showTaskAdded(Task task, TaskList tasks) {
        System.out.println("got it! you have a new task: ");
        System.out.println(task);
        if (tasks.size() == 1) {
            System.out.println("you now have 1 task in the list!");
        } else {
            System.out.println("you now have " + tasks.size() + " tasks in the list!");
        }
    }
}
