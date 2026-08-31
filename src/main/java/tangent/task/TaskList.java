package tangent.task;

import java.util.ArrayList;
import java.util.List;

/**
 * Owns the in-memory collection of tasks and its basic operations.
 */
public class TaskList {
    private final List<Task> tasks;

    /** Creates an empty task list. */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /** Creates a task list containing the supplied loaded tasks. */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /** Adds a task to the end of this list. */
    public void add(Task task) {
        tasks.add(task);
    }

    /** Restores a task at its former position in this list. */
    public void add(int index, Task task) {
        tasks.add(index, task);
    }

    /** Removes and returns the task at the specified zero-based index. */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /** Removes and returns the final task in this list. */
    public Task removeLast() {
        return tasks.removeLast();
    }

    /** Returns the task at the specified zero-based index. */
    public Task get(int index) {
        return tasks.get(index);
    }

    /** Returns the number of tasks in this list. */
    public int size() {
        return tasks.size();
    }

    /** Returns whether this list contains no tasks. */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /** Returns a copy suitable for passing to persistence code. */
    public List<Task> toList() {
        return new ArrayList<>(tasks);
    }
}
