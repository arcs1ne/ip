package tangent.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import tangent.exception.TangentException;
import tangent.task.Deadline;
import tangent.task.Event;
import tangent.task.Task;
import tangent.task.ToDo;
public class StorageTest {
    @TempDir
    Path tempDir;

    @Test
    public void load_missingFile_emptyListReturnedAndFileCreated() throws TangentException {
        Path dataFile = tempDir.resolve("data").resolve("tangent.txt");
        Storage storage = new Storage(dataFile.toString());
        List<Task> tasks = storage.load();
        assertTrue(tasks.isEmpty());
        assertTrue(Files.exists(dataFile));
    }

    @Test
    public void load_validRecords_correctTaskTypesFieldsAndStatusesReturned() throws IOException, TangentException {
        Path dataFile = tempDir.resolve("tangent.txt");
        Files.writeString(dataFile, String.join(System.lineSeparator(),
                "T | 0 | borrow book",
                "D | 1 | return book | 2/12/2019 1800",
                "E | 0 | project meeting | 3/12/2019 0900 | 3/12/2019 1100"));

        Storage storage = new Storage(dataFile.toString());
        List<Task> tasks = storage.load();

        assertEquals(3, tasks.size());

        ToDo todo = assertInstanceOf(ToDo.class, tasks.get(0));
        assertEquals("borrow book", todo.getDescription());
        assertFalse(todo.isDone());

        Deadline deadline = assertInstanceOf(Deadline.class, tasks.get(1));
        assertEquals("return book", deadline.getDescription());
        assertEquals(LocalDateTime.of(2019, 12, 2, 18, 0), deadline.getBy());
        assertTrue(deadline.isDone());

        Event event = assertInstanceOf(Event.class, tasks.get(2));
        assertEquals("project meeting", event.getDescription());
        assertEquals(LocalDateTime.of(2019, 12, 3, 9, 0), event.getFrom());
        assertEquals(LocalDateTime.of(2019, 12, 3, 11, 0), event.getTo());
        assertFalse(event.isDone());
    }

    @Test
    public void load_unknownTaskType_exceptionThrown() throws Exception {
        Path dataFile = tempDir.resolve("tangent.txt");
        Files.writeString(dataFile, "X | 0 | thing");

        Storage storage = new Storage(dataFile.toString());

        TangentException exception = assertThrows(TangentException.class, storage::load);

        assertEquals("data file contains an unknown task type: X", exception.getMessage());
    }

    @Test
    public void load_invalidStatus_exceptionThrown() throws Exception {
        Path dataFile = tempDir.resolve("tangent.txt");
        Files.writeString(dataFile, "T | 2 | borrow book");

        Storage storage = new Storage(dataFile.toString());

        TangentException exception = assertThrows(TangentException.class, storage::load);

        assertEquals("data file contains an invalid task record: T | 2 | borrow book",
                exception.getMessage());
    }

    @Test
    public void load_invalidDate_exceptionThrown() throws Exception {
        Path dataFile = tempDir.resolve("tangent.txt");
        Files.writeString(dataFile, "D | 0 | return book | 31/2/2019 1800");

        Storage storage = new Storage(dataFile.toString());

        TangentException exception = assertThrows(TangentException.class, storage::load);

        assertEquals(
                "bad date format :( ensure your dates are in the format "
                        + "DD/MM/YYYY HHmm (example: 07/06/2026 2200)",
                exception.getMessage());
    }
}
