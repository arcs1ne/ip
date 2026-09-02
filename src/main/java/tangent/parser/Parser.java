package tangent.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import tangent.command.AddCommand;
import tangent.command.DeleteCommand;
import tangent.command.ExitCommand;
import tangent.command.ListCommand;
import tangent.command.MarkCommand;
import tangent.command.UnmarkCommand;
import tangent.command.Command;
import tangent.exception.TangentException;
import tangent.task.Deadline;
import tangent.task.Event;
import tangent.task.Task;
import tangent.task.ToDo;

/** Interprets and validates commands entered by the user. */
public class Parser {
    /** The specified format of the date the user inputs for {@code Event} and {@code Deadline} objects. */
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter
            .ofPattern("d/M/uuuu HHmm")
            .withResolverStyle(ResolverStyle.STRICT);
    /** The separator to be used in the data file to separate the details of a task. */
    private static final String FIELD_SEPARATOR = " | ";
    /** The marker to identify when a deadline should follow in a {@code Deadline} object. */
    private static final String BY_MARKER = " /by ";
    /** The marker to identify when a start time should follow in an {@code Event} object. */
    private static final String FROM_MARKER = " /from ";
    /** The marker to identify when an end time should follow in an {@code Event} object. */
    private static final String TO_MARKER = " /to ";

    /**
     * Converts a complete user command into the command object that performs its action.
     *
     * @param fullCommand The full response entered by the user.
     * @throws TangentException if the command is not part of the recognised keywords for existing commands,
     * or the task description is empty for {@code ToDo}, {@code Deadline} and {@code Event} objects.
     */
    public static Command parse(String fullCommand) throws TangentException {
        String[] inputs = fullCommand.split(" ", 2);
        CommandTypes type = CommandTypes.fromInput(inputs[0]);
        switch (type) {
            case MARK:
                return new MarkCommand(parseTaskIndex(inputs));
            case UNMARK:
                return new UnmarkCommand(parseTaskIndex(inputs));
            case DELETE:
                return new DeleteCommand(parseTaskIndex(inputs));
            case TODO:
            case DEADLINE:
            case EVENT:
                if (inputs.length < 2 || inputs[1].trim().isEmpty()) {
                    throw new TangentException("please provide a task description!");
                }
                return new AddCommand(parseTask(inputs[1], type));
            case LIST:
                return new ListCommand();
            case BYE:
                return new ExitCommand();
            default:
                throw new TangentException("invalid command!");
        }
    }

    /**
     * Validates a 1-based task index input and returns its 0-based index.
     *
     * @param inputs An array containing the command type followed by the rest of the user's command.
     * @throws TangentException if a task number is not provided, an invalid task number is provided,
     * or the task number is out of bounds.
     */
    public static int parseTaskIndex(String[] inputs) throws TangentException {
        if (inputs.length < 2) {
            throw new TangentException("please provide a valid task number!");
        }
        try {
            int taskIndex = Integer.parseInt(inputs[1]) - 1;
            if (taskIndex < 0) {
                throw new TangentException("please provide a valid task number!");
            }
            return taskIndex;
        } catch (NumberFormatException e) {
            throw new TangentException("please provide a valid task number!");
        }
    }

    /**
     * Creates a new Task object based on the user's input.
     *
     * @param details The string that comes after the command type.
     * @param type The command type parsed from the user's command.
     * @return {@code ToDo}, {@code Deadline} or {@code Event} object corresponding to {@code type}.
     * @throws TangentException if {@code type} cannot be understood or if {@code details} contains invalid characters,
     * is not in the specified format, uses a wrong date format, or contains an end time earlier than its start time.
     */
    public static Task parseTask(String details, CommandTypes type) throws TangentException {
        String description = details.trim();
        switch (type) {
            case TODO:
                validateDescription(description);
                return new ToDo(description);
            case DEADLINE:
                return parseDeadline(details);
            case EVENT:
                return parseEvent(details);
            default:
                throw new TangentException("unknown task type!");
        }
    }

    /**
     * Creates a new {@code Deadline} object based on the description and the deadline given in {@code details}.
     *
     * @throws TangentException if the details are not in the correct format or contains an invalid date format.
     */
    private static Deadline parseDeadline(String details) throws TangentException {
        int byIndex = details.indexOf(BY_MARKER);
        if (byIndex <= 0 || details.indexOf(BY_MARKER, byIndex + BY_MARKER.length()) != -1) {
            throw new TangentException("please use: deadline DESCRIPTION /by TIME");
        }
        String description = details.substring(0, byIndex).trim();
        String by = details.substring(byIndex + BY_MARKER.length()).trim();
        if (description.isEmpty() || by.isEmpty()) {
            throw new TangentException("please use: deadline DESCRIPTION /by TIME");
        }
        validateDescription(description);
        return new Deadline(description, parseDateTime(by));
    }

    /**
     * Creates a new {@code Event} object based on the description, start time and end time given in {@code details}.
     *
     * @throws TangentException if the details are not in the correct format, contains an invalid date format,
     * or contains an end time that is earlier than its start time.
     */
    private static Event parseEvent(String details) throws TangentException {
        int fromIndex = details.indexOf(FROM_MARKER);
        int toIndex = details.indexOf(TO_MARKER);
        if (fromIndex <= 0 || toIndex <= fromIndex
                || details.indexOf(FROM_MARKER, fromIndex + FROM_MARKER.length()) != -1
                || details.indexOf(TO_MARKER, toIndex + TO_MARKER.length()) != -1) {
            throw new TangentException("please use: event DESCRIPTION /from START /to END");
        }
        String description = details.substring(0, fromIndex).trim();
        String from = details.substring(fromIndex + FROM_MARKER.length(), toIndex).trim();
        String to = details.substring(toIndex + TO_MARKER.length()).trim();
        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new TangentException("please use: event DESCRIPTION /from START /to END");
        }
        validateDescription(description);
        LocalDateTime fromDateTime = parseDateTime(from);
        LocalDateTime toDateTime = parseDateTime(to);
        if (!toDateTime.isAfter(fromDateTime)) {
            throw new TangentException("your end time must be later than your start time!");
        }
        return new Event(description, fromDateTime, toDateTime);
    }

    /**
     * Ensures descriptions do not contain the {@code FIELD_SEPARATOR}.
     *
     * @throws TangentException if the description contains the {@code FIELD_SEPARATOR}.
     */
    private static void validateDescription(String description) throws TangentException {
        if (description.contains(FIELD_SEPARATOR)) {
            throw new TangentException("task descriptions cannot contain " + FIELD_SEPARATOR + "!");
        }
    }

    /**
     * Parses a user-supplied date and time.
     *
     * @throws TangentException if the provided date and time does not match the {@code INPUT_FORMATTER}.
     */
    private static LocalDateTime parseDateTime(String input) throws TangentException {
        try {
            return LocalDateTime.parse(input.trim(), INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new TangentException("bad date format :( ensure your dates are in the format"
                    + "DD/MM/YYYY HHmm (example: 07/06/2026 2200)");
        }
    }
}
