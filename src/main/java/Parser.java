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

    /** Identifies the command represented by the first word of the user's input. */
    public CommandTypes parseCommandType(String input) throws TangentException {
        return CommandTypes.fromInput(input);
    }

    /** Validates a one-based task number and returns its zero-based index. */
    public int parseTaskIndex(String[] inputs, TaskList tasks) throws TangentException {
        if (inputs.length < 2) {
            throw new TangentException("please provide a valid task number!");
        }
        try {
            int taskIndex = Integer.parseInt(inputs[1]) - 1;
            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                throw new TangentException("please provide a valid task number!");
            }
            return taskIndex;
        } catch (NumberFormatException e) {
            throw new TangentException("please provide a valid task number!");
        }
    }

    /** Creates a task from the details supplied after a task-creation command. */
    public Task parseTask(String details, CommandTypes type) throws TangentException {
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
    private Deadline parseDeadline(String details) throws TangentException {
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
    private Event parseEvent(String details) throws TangentException {
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
    private void validateDescription(String description) throws TangentException {
        if (description.contains(FIELD_SEPARATOR)) {
            throw new TangentException("task descriptions cannot contain ' | '!");
        }
    }

    /** Parses a user-supplied date and time. */
    private LocalDateTime parseDateTime(String input) throws TangentException {
        try {
            return LocalDateTime.parse(input.trim(), INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new TangentException("bad date format :( ensure your dates are in the format DD/MM/YYYY HHmm (example: 07/06/2026 2200)");
        }
    }
}
