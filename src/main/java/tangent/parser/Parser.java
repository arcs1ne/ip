package tangent.parser;

import tangent.command.*;
import tangent.exception.TangentException;
import tangent.task.Deadline;
import tangent.task.Event;
import tangent.task.Task;
import tangent.task.ToDo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Interprets and validates commands entered by the user.
 */
public class Parser {
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter
            .ofPattern("d/M/uuuu HHmm")
            .withResolverStyle(ResolverStyle.STRICT);
    private static final String FIELD_SEPARATOR = " | ";
    private static final String BY_MARKER = " /by ";
    private static final String FROM_MARKER = " /from ";
    private static final String TO_MARKER = " /to ";

    /**
     * Converts a complete user command into the command object that performs its action.
     */
    public static Command parse(String fullCommand) throws TangentException {
        String[] inputs = fullCommand.split(" ", 2);
        CommandTypes type = CommandTypes.fromInput(inputs[0]);
        switch (type) {
            case MARK:
                return new MarkCommand(parseTaskIndex(inputs));
            case UNMARK:
                return new UnmarkCommand(parseTaskIndex(inputs));
            case FIND:
                if (inputs.length < 2 || inputs[1].trim().isEmpty()) {
                    throw new TangentException("please provide a keyword to search for!");
                }
                return new FindCommand(inputs[1].trim());
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

    /** Validates a one-based task number's format and returns its zero-based index. */
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

    /** Creates a task from the details supplied after a task-creation command. */
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

    /** Parses the description and due date for a deadline task. */
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

    /** Parses the description, start time, and end time for an event task. */
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

    /** Rejects descriptions that cannot be safely stored in the record format. */
    private static void validateDescription(String description) throws TangentException {
        if (description.contains(FIELD_SEPARATOR)) {
            throw new TangentException("task descriptions cannot contain ' | '!");
        }
    }

    /** Parses a user-supplied date and time. */
    private static LocalDateTime parseDateTime(String input) throws TangentException {
        try {
            return LocalDateTime.parse(input.trim(), INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new TangentException("bad date format :( ensure your dates are in the format DD/MM/YYYY HHmm (example: 07/06/2026 2200)");
        }
    }
}
