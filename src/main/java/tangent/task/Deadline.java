package tangent.task;

import tangent.storage.Storage;
import tangent.ui.Ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/** Represents a task with a description and an end time. */
public class Deadline extends Task {

    /** The deadline of the task. */
    private final LocalDateTime by;
    /** Specifies the format of the date output to be displayed in {@link Deadline#toString()}. */
    public static final DateTimeFormatter OUTPUT_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma", Locale.ENGLISH);

    /** Creates a new Deadline object with a description and an end time. */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /** Returns the deadline date and time. */
    public LocalDateTime getBy() {
        return by;
    }

    /** Converts the Deadline object to a String to be displayed to the user. */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMATTER) + ")";
    }
}
