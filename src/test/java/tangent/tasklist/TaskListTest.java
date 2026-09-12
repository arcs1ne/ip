package tangent.tasklist;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import tangent.task.Task;
import tangent.task.TaskList;
import tangent.task.ToDo;

public class TaskListTest {
    @Test
    public void find_matchingTasks_returnsTasksInOriginalOrder() {
        TaskList tasks = new TaskList();
        Task first = new ToDo("read a book");
        Task second = new ToDo("complete assignment");
        Task third = new ToDo("return the book");

        tasks.add(first);
        tasks.add(second);
        tasks.add(third);

        TaskList matches = tasks.find("book");

        assertEquals(2, matches.size());
        assertEquals(first, matches.get(0));
        assertEquals(third, matches.get(1));
    }

    @Test
    public void find_keywordMatchingIsCaseInsensitive_returnsMatchingTasks() {
        TaskList tasks = new TaskList();
        tasks.add(new ToDo("Read a Book"));

        TaskList matches = tasks.find("book");

        assertEquals(1, matches.size());
        assertEquals("Read a Book", matches.get(0).getDescription());
    }

    @Test
    public void find_noMatchingTasks_returnsEmptyList() {
        TaskList tasks = new TaskList();
        tasks.add(new ToDo("read a book"));

        TaskList matches = tasks.find("movie");

        assertEquals(0, matches.size());
    }

    @Test
    public void removeAtIndexes_usesOriginalIndexesAndReturnsOriginalOrder() {
        Task first = new ToDo("first");
        Task second = new ToDo("second");
        Task third = new ToDo("third");
        Task fourth = new ToDo("fourth");
        TaskList tasks = new TaskList(first, second, third, fourth);

        List<Task> removedTasks = tasks.removeAtIndexes(List.of(3, 1));

        assertEquals(List.of(second, fourth), removedTasks);
        assertEquals(List.of(first, third), tasks.toList());
    }

    @Test
    public void restoreAtIndexes_restoresTasksToOriginalPositions() {
        Task first = new ToDo("first");
        Task second = new ToDo("second");
        Task third = new ToDo("third");
        Task fourth = new ToDo("fourth");
        TaskList tasks = new TaskList(first, second, third, fourth);
        List<Integer> indexes = List.of(3, 1);
        List<Task> removedTasks = tasks.removeAtIndexes(indexes);

        tasks.restoreAtIndexes(indexes, removedTasks);

        assertEquals(List.of(first, second, third, fourth), tasks.toList());
    }
}
