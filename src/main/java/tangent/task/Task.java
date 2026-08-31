package tangent.task;

public class Task {
    private final String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /** Returns the task description for persistence. */
    public String getDescription() {
        return description;
    }

    /** Returns whether this task is complete for persistence. */
    public boolean isDone() {
        return isDone;
    }

    public void markAsDone() {
        this.isDone = true; // marks task as done
    }

    public void markAsUndone() {
        this.isDone = false; // marks task as undone
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}
