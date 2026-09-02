package tangent.task;

import tangent.exception.TangentException;

import java.util.ArrayList;
import java.util.List;

/**
 * Owns the in-memory collection of tasks and its basic operations.
 */
public class TaskList {
    /** The task list containing the tasks currently held in memory. */
    private final List<Task> tasks;

    /** Creates a task list containing the supplied loaded tasks. */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /** Adds a task to the end of this list. */
    public void add(Task task) {
        tasks.add(task);
    }

    /** Adds a task at the specified 0-based index in this list. */
    public void add(int index, Task task) {
        tasks.add(index, task);
    }

    /** Removes and returns the task at the specified 0-based index. */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /** Removes and returns the final task in this list. */
    public Task removeLast() {
        return tasks.removeLast();
    }

    /** Returns the task at the specified 0-based index. */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Ensures the supplied zero-based index is valid for the given task list.
     *
     * @throws TangentException if the index is out of bounds.
     */
    public void validateIndex(int index) throws TangentException {
        if (index < 0 || index >= tasks.size()) {
            throw new TangentException("please provide a valid task number!");
        }
    }

    /** Returns the number of tasks in this list. */
    public int size() {
        return tasks.size();
    }

    /** Returns whether this list contains no tasks. */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /** Returns a shallow copy of the current task list. */
    public List<Task> toList() {
        return new ArrayList<>(tasks);
    }

    /** Returns a list of tasks with descriptions containing the {@code keyword} (case-insensitive). */
    public TaskList find(String keyword) {
        TaskList matchingTasks = new TaskList();
        for (Task t: tasks) {
            if (t.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(t);
            }
        }
        return matchingTasks;
    }
}
