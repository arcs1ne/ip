import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;

public class Tangent {
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter
            .ofPattern("d/M/uuuu HHmm")
            .withResolverStyle(ResolverStyle.STRICT);
    private static final Path DATA_FILE = Path.of("data", "tangent.txt");
    private static final String FIELD_SEPARATOR = " | ";
    private static final String DIVIDER = "____________________________________________________________";

    public static void main(String[] args) {
        String banner = """
                ████████╗ █████╗ ███╗   ██╗ ██████╗ ███████╗███╗   ██╗████████╗
                ╚══██╔══╝██╔══██╗████╗  ██║██╔════╝ ██╔════╝████╗  ██║╚══██╔══╝
                   ██║   ███████║██╔██╗ ██║██║  ███╗█████╗  ██╔██╗ ██║   ██║
                   ██║   ██╔══██║██║╚██╗██║██║   ██║██╔══╝  ██║╚██╗██║   ██║
                   ██║   ██║  ██║██║ ╚████║╚██████╔╝███████╗██║ ╚████║   ██║
                   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚══════╝╚═╝  ╚═══╝   ╚═╝""";
        System.out.println(DIVIDER);
        System.out.println(banner);
        System.out.println("good morning/afternoon/evening ^-^ I'm TANGENT.\nwhat do you want me to do?");
        System.out.println(DIVIDER);

        ArrayList<Task> tasks;
        try {
            tasks = initializeTasks();
        } catch (TangentException e) {
            System.out.println(e.getMessage());
            return;
        }
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
                    CommandTypes commandTypes = CommandTypes.fromInput(inputs[0]);
                    switch (commandTypes) {
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
                            Task removedTask = tasks.remove(delIdx);
                            try {
                                saveTasks(tasks);
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
                                handleTask(inputs[1], tasks, commandTypes);
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
    private static void updateTaskStatus(ArrayList<Task> tasks, int taskIndex, boolean isDone) throws TangentException {
        Task task = tasks.get(taskIndex);
        boolean wasDone = task.isDone();
        if (isDone) {
            task.markAsDone();
        }
        try {
            saveTasks(tasks);
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
    private static void handleTask(String details, ArrayList<Task> tasks, CommandTypes type) throws TangentException {
        Task t;
        final String byMarker = " /by ";
        final String fromMarker = " /from ";
        final String toMarker = " /to ";
        String description = details.trim();

        switch (type) {
            case TODO:
                validateDescription(description);
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
                validateDescription(deadlineDescription);
                t = new Deadline(deadlineDescription, parseDateTime(by));
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
                validateDescription(eventDescription);
                LocalDateTime fromDateTime = parseDateTime(from);
                LocalDateTime toDateTime = parseDateTime(to);
                if (!toDateTime.isAfter(fromDateTime)) {
                    throw new TangentException("your end time must be later than your start time!");
                }
                t = new Event(eventDescription, fromDateTime, toDateTime);
                break;

            default:
                throw new TangentException("unknown task type!");
        }
        tasks.add(t);
        try {
            saveTasks(tasks);
        } catch (TangentException e) {
            tasks.removeLast();
            throw e;
        }
        System.out.println("got it! you have a new task: ");
        System.out.println(t);
        if (tasks.size() == 1) {
            System.out.println("you now have 1 task in the list!");
        } else {
            System.out.println("you now have " + tasks.size() + " tasks in the list!");
        }
        System.out.println(DIVIDER);
    }

    /** Initializes a list of tasks based on the text file in the hard disk.
     * Creates a new folder and/or text file if it is not present..
     *
     * @throws TangentException if the file is not found or has formatting errors.
     */
    private static ArrayList<Task> initializeTasks() throws TangentException {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            Files.createDirectories(DATA_FILE.getParent());
            if (Files.notExists(DATA_FILE)) {
                Files.createFile(DATA_FILE);
            }
            try (BufferedReader br = Files.newBufferedReader(DATA_FILE)) {
                String line;
                while ((line = br.readLine()) != null) {
                    tasks.add(getTask(line));
                }
            }
        } catch (IOException e) {
            throw new TangentException("can't find the file :(");
        }
        return tasks;
    }

    private static Task getTask(String line) throws TangentException {
        String[] data = line.split(" \\| ", -1);
        if (data.length < 3 || (!data[1].equals("0") && !data[1].equals("1"))) {
            throw new TangentException("data file contains an invalid task record: " + line);
        }
        Task t;
        switch (data[0]) {
        case "T":
            requireFieldCount(data, 3, line);
            t = new ToDo(data[2]);
            break;
        case "D":
            requireFieldCount(data, 4, line);
            t = new Deadline(data[2], parseDateTime(data[3]));
            break;
        case "E":
            requireFieldCount(data, 5, line);
            t = new Event(data[2], parseDateTime(data[3]), parseDateTime(data[4]));
            break;
        default:
            throw new TangentException("data file contains an unknown task type: " + data[0]);
        }
        if (data[1].equals("1")) {
            t.markAsDone();
        }
        return t;
    }

    private static void saveTasks(List<Task> tasks) throws TangentException {
        List<String> records = new ArrayList<>();
        for (Task task : tasks) {
            records.add(toRecord(task));
        }
        try {
            Files.write(DATA_FILE, records);
        } catch (IOException e) {
            throw new TangentException("unable to save your tasks :(");
        }
    }

    private static String toRecord(Task task) {
        String status = task.isDone() ? "1" : "0";
        if (task instanceof ToDo) {
            return "T" + FIELD_SEPARATOR + status + FIELD_SEPARATOR + task.getDescription();
        }
        if (task instanceof Deadline deadline) {
            return "D" + FIELD_SEPARATOR + status + FIELD_SEPARATOR + task.getDescription()
                    + FIELD_SEPARATOR + deadline.getBy().format(INPUT_FORMATTER);
        }
        Event event = (Event) task;
        return "E" + FIELD_SEPARATOR + status + FIELD_SEPARATOR + task.getDescription()
                + FIELD_SEPARATOR + event.getFrom().format(INPUT_FORMATTER)
                + FIELD_SEPARATOR + event.getTo().format(INPUT_FORMATTER);
    }

    private static void validateDescription(String description) throws TangentException {
        if (description.contains(FIELD_SEPARATOR)) {
            throw new TangentException("task descriptions cannot contain ' | '!");
        }
    }

    private static void requireFieldCount(String[] data, int expectedCount, String line) throws TangentException {
        if (data.length != expectedCount) {
            throw new TangentException("data file contains an invalid task record: " + line);
        }
    }

    public static LocalDateTime parseDateTime(String input) throws TangentException {
        try {
            return LocalDateTime.parse(input.trim(), INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new TangentException("bad date format :( ensure your dates are in the format DD/MM/YYYY HHmm (example: 07/06/2026 2200)");
        }
    }
}
