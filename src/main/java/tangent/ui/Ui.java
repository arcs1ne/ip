package tangent.ui;

import java.util.Scanner;
import java.util.function.Consumer;

import tangent.task.Task;
import tangent.task.TaskList;

/** Handles console input and output messages. */
public class Ui {
    /** A line used to separate responses in the console. */
    private static final String DIVIDER = "____________________________________________________________";
    /** The banner shown when loading up the program. */
    private static final String BANNER = """
            ████████╗ █████╗ ███╗   ██╗ ██████╗ ███████╗███╗   ██╗████████╗
            ╚══██╔══╝██╔══██╗████╗  ██║██╔════╝ ██╔════╝████╗  ██║╚══██╔══╝
               ██║   ███████║██╔██╗ ██║██║  ███╗█████╗  ██╔██╗ ██║   ██║
               ██║   ██╔══██║██║╚██╗██║██║   ██║██╔══╝  ██║╚██╗██║   ██║
               ██║   ██║  ██║██║ ╚████║╚██████╔╝███████╗██║ ╚████║   ██║
               ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚══════╝╚═╝  ╚═══╝   ╚═╝""";

    /** Receives formatted messages for display. */
    private final Consumer<String> output;

    /** Creates a UI that writes messages to standard output. */
    public Ui() {
        this(System.out::println);
    }

    /**
     * Creates a UI that sends messages to the supplied output handler.
     *
     * @param output handler that receives each formatted message
     */
    public Ui(Consumer<String> output) {
        this.output = output;
    }

    /** Displays the greeting shown when the program starts. */
    public void showWelcome() {
        showDivider();
        display(BANNER);
        display("good morning/afternoon/evening ^-^ I'm TANGENT.\nwhat do you want me to do?");
        showDivider();
    }

    /** Displays the divider used to separate responses in the console. */
    public void showDivider() {
        display(DIVIDER);
    }

    /** Reads and trims one command entered by the user. */
    public String readCommand(Scanner scanner) {
        return scanner.nextLine().trim();
    }

    /** Displays an error message from the application. */
    public void showError(String message) {
        display(message);
    }

    /** Displays the tasks currently stored in the task list with 1-based indexing. */
    public void showTaskList(TaskList tasks) {
        if (tasks.isEmpty()) {
            display("no tasks yet!");
        }
        for (int i = 0; i < tasks.size(); i++) {
            display((i + 1) + ". " + tasks.get(i));
        }
    }

    /** Displays the farewell message shown when the program exits. */
    public void showGoodbye() {
        display("bye o/ hope to see you again soon");
    }

    /**
     * Displays confirmation that a task's completion status has changed.
     *
     * @param isDone whether the task was marked as done.
     */
    public void showTaskStatusChanged(boolean isDone) {
        if (isDone) {
            display("i've marked it as done!");
        } else {
            display("i've marked it as undone!");
        }
    }

    /** Displays confirmation that a task was removed and displays the remaining task count. */
    public void showTaskDeleted(Task removedTask, TaskList tasks) {
        display("got it! i've removed this task:");
        display(removedTask.toString());
        if (tasks.isEmpty()) {
            display("you now have no tasks in the list!");
        } else if (tasks.size() == 1) {
            display("you now have 1 task in the list!");
        } else {
            display("you now have " + tasks.size() + " tasks in the list!");
        }
    }

    /** Displays confirmation that a task was added and displays the new task count. */
    public void showTaskAdded(Task task, TaskList tasks) {
        display("got it! you have a new task: ");
        display(task.toString());
        if (tasks.size() == 1) {
            display("you now have 1 task in the list!");
        } else {
            display("you now have " + tasks.size() + " tasks in the list!");
        }
    }

    /**
     * Displays all tasks matching the specified {@code keyword}, or a no-match message.
     *
     * @param keyword the keyword used for the search
     * @param matches the matching tasks to display
     */
    public void showMatchingTasks(String keyword, TaskList matches) {
        if (matches.isEmpty()) {
            display("there were no matching tasks in the task list!");
        } else {
            display("here are the tasks matching the keyword " + keyword + ":");
            showTaskList(matches);
        }
    }

    /** Sends one formatted message to configured output handler. */
    protected void display(String message) {
        output.accept(message);
    }
}
