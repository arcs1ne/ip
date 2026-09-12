package tangent.command;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import tangent.exception.TangentException;
import tangent.storage.Storage;
import tangent.task.TaskList;
import tangent.task.ToDo;
import tangent.ui.Ui;

public class BatchCommandTest {
    @TempDir
    Path tempDir;

    @Test
    public void delete_saveFailure_batchIsRolledBack() {
        ToDo first = new ToDo("first");
        ToDo second = new ToDo("second");
        ToDo third = new ToDo("third");
        TaskList tasks = new TaskList(first, second, third);
        Storage storage = new Storage(tempDir.toString());

        assertThrows(TangentException.class, () -> new DeleteCommand(List.of(0, 2))
                .execute(tasks, new Ui(_ -> { }), storage));

        assertFalse(tasks.isEmpty());
        assertFalse(tasks.get(0).isDone());
        assertFalse(tasks.get(1).isDone());
        assertFalse(tasks.get(2).isDone());
    }

    @Test
    public void mark_saveFailure_batchStatusesAreRolledBack() {
        TaskList tasks = new TaskList(new ToDo("first"), new ToDo("second"));
        Storage storage = new Storage(tempDir.toString());

        assertThrows(TangentException.class, () -> new MarkCommand(List.of(0, 1))
                .execute(tasks, new Ui(_ -> { }), storage));

        assertFalse(tasks.get(0).isDone());
        assertFalse(tasks.get(1).isDone());
    }
}
