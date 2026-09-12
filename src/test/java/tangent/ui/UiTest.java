package tangent.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import tangent.task.TaskList;
import tangent.task.ToDo;

public class UiTest {
    @Test
    public void showTaskStatusChanged_batchDisplaysSummaryAndTasks() {
        ToDo first = new ToDo("first");
        ToDo second = new ToDo("second");
        List<String> messages = new ArrayList<>();
        Ui ui = new Ui(messages::add);

        ui.showTaskStatusChanged(true, List.of(first, second));

        assertEquals(List.of("i've marked 2 tasks as done!", "[T][ ] first", "[T][ ] second"), messages);
    }

    @Test
    public void showTasksDeleted_batchDisplaysTasksAndRemainingCount() {
        ToDo first = new ToDo("first");
        ToDo second = new ToDo("second");
        List<String> messages = new ArrayList<>();
        Ui ui = new Ui(messages::add);

        ui.showTasksDeleted(List.of(first, second), new TaskList(new ToDo("remaining")));

        assertEquals(List.of("got it! i've removed these tasks:", "[T][ ] first", "[T][ ] second",
                "removed 2 tasks, 1 tasks remaining!"), messages);
    }
}
