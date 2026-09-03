package tangent.storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

import tangent.exception.TangentException;
import tangent.task.Deadline;
import tangent.task.Event;
import tangent.task.Task;
import tangent.task.ToDo;



/**
 * Loads tasks from and saves tasks to the data file specified by a file path.
 */
public class Storage {
    /**
     * The specified format of the date to be stored in {@code dataFile}, ensuring that the parsed values are in range.
     */
    private static final DateTimeFormatter FILE_DATE_FORMATTER = DateTimeFormatter
            .ofPattern("d/M/uuuu HHmm")
            .withResolverStyle(ResolverStyle.STRICT);
    /** The separator to be used in the data file to separate the details of a task. */
    private static final String FIELD_SEPARATOR = " | ";
    /** The path to the specified dataFile. */
    private final Path dataFile;

    /**
     * Creates a storage object backed by the data file at the given path.
     *
     * @param filePath The path of the file used to store tasks.
     */
    public Storage(String filePath) {
        this.dataFile = Path.of(filePath);
    }

    /**
     * Creates the data file if required, then returns every task stored in it.
     *
     * @throws TangentException if the data file cannot be created, read or parsed.
     */
    public ArrayList<Task> load() throws TangentException {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            Files.createDirectories(dataFile.getParent());
            if (Files.notExists(dataFile)) {
                Files.createFile(dataFile);
            }
            try (BufferedReader reader = Files.newBufferedReader(dataFile)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    tasks.add(toTask(line));
                }
            }
        } catch (IOException e) {
            throw new TangentException("can't find the file :(");
        }
        return tasks;
    }

    /**
     * Saves every task as a record in the data file by overwriting existing file contents.
     *
     * @param tasks The list of tasks to save in the data file.
     * @throws TangentException if the data file cannot be written to.
     */
    public void save(List<Task> tasks) throws TangentException {
        List<String> records = new ArrayList<>();
        for (Task task : tasks) {
            records.add(toRecord(task));
        }
        try {
            Files.write(dataFile, records);
        } catch (IOException e) {
            throw new TangentException("unable to save your tasks :(");
        }
    }

    /**
     * Converts one saved record into its corresponding task.
     *
     * @throws TangentException if the data file contains an invalid task record.
     */
    private Task toTask(String line) throws TangentException {
        String[] data = line.split(" \\| ", -1);
        if (data.length < 3 || (!data[1].equals("0") && !data[1].equals("1"))) {
            throw new TangentException("data file contains an invalid task record: " + line);
        }
        Task task;
        switch (data[0]) {
            case "T":
                requireFieldCount(data, 3, line);
                task = new ToDo(data[2]);
                break;
            case "D":
                requireFieldCount(data, 4, line);
                task = new Deadline(data[2], parseFileDateTime(data[3]));
                break;
            case "E":
                requireFieldCount(data, 5, line);
                task = new Event(data[2], parseFileDateTime(data[3]), parseFileDateTime(data[4]));
                break;
            default:
                throw new TangentException("data file contains an unknown task type: " + data[0]);
        }
        if (data[1].equals("1")) {
            task.markAsDone();
        }
        return task;
    }

    /**
     * Converts a task into one saved record of the correct format in the data file.
     */
    private String toRecord(Task task) {
        String status = task.isDone() ? "1" : "0";
        if (task instanceof ToDo) {
            return "T" + FIELD_SEPARATOR + status + FIELD_SEPARATOR + task.getDescription();
        }
        if (task instanceof Deadline deadline) {
            return "D" + FIELD_SEPARATOR + status + FIELD_SEPARATOR + task.getDescription()
                    + FIELD_SEPARATOR + deadline.getBy().format(FILE_DATE_FORMATTER);
        }
        Event event = (Event) task;
        return "E" + FIELD_SEPARATOR + status + FIELD_SEPARATOR + task.getDescription()
                + FIELD_SEPARATOR + event.getFrom().format(FILE_DATE_FORMATTER)
                + FIELD_SEPARATOR + event.getTo().format(FILE_DATE_FORMATTER);
    }

    /**
     * Checks that a saved record contains exactly its expected number of fields.
     * {@code ToDo} objects require 3 fields, {@code Deadline} objects require 4 fields
     * and {@code Event} objects require 5 fields.
     *
     * @throws TangentException if the record does not exactly match the number of fields required.
     */
    private void requireFieldCount(String[] data, int expectedCount, String line) throws TangentException {
        if (data.length != expectedCount) {
            throw new TangentException("data file contains an invalid task record: " + line);
        }
    }

    /**
     * Parses a date stored in the data file.
     *
     * @throws TangentException if the date stored in the data file does not match {@code FILE_DATE_FORMATTER}.
     */
    private LocalDateTime parseFileDateTime(String input) throws TangentException {
        try {
            return LocalDateTime.parse(input.trim(), FILE_DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new TangentException("bad date format :( ensure your dates are in the format "
                    + "DD/MM/YYYY HHmm (example: 07/06/2026 2200)");
        }
    }
}
