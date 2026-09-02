package tangent.parser;

import org.junit.jupiter.api.Test;
import tangent.command.*;
import tangent.exception.TangentException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Tests that the parser creates commands without relying on application state. */
public class ParserTest {
    @Test
    public void parseCommand_validCommands_correctCommandTypesReturned() throws TangentException {
        assertInstanceOf(MarkCommand.class, Parser.parse("mark 1"));
        assertInstanceOf(UnmarkCommand.class, Parser.parse("unmark 1"));
        assertInstanceOf(DeleteCommand.class, Parser.parse("delete 1"));
        assertInstanceOf(AddCommand.class, Parser.parse("todo read book"));
        assertInstanceOf(AddCommand.class, Parser.parse("deadline return book /by 2/12/2019 1800"));
        assertInstanceOf(AddCommand.class, Parser.parse("event meeting /from 2/12/2019 1800 /to 2/12/2019 1900"));
        assertInstanceOf(ListCommand.class, Parser.parse("list"));
        assertInstanceOf(ExitCommand.class, Parser.parse("bye"));
        assertInstanceOf(FindCommand.class, Parser.parse("find stuff"));
    }

    @Test
    public void parseTaskNumber_invalidIndex_exceptionThrown() {
        assertInvalidTaskNumber("mark");
        assertInvalidTaskNumber("unmark abc");
        assertInvalidTaskNumber("delete 0");
        assertInvalidTaskNumber("mark -1");
    }

    @Test
    public void parseTask_missingDescription_exceptionThrown() {
        assertMissingDescription("todo");
        assertMissingDescription("deadline");
        assertMissingDescription("event");
    }

    @Test
    public void parseFind_missingKeyword_exceptionThrown() {
        TangentException exception = assertThrows(TangentException.class, () -> Parser.parse("find"));
        assertEquals("please provide a keyword to search for!", exception.getMessage());
    }

    @Test
    public void parse_unknownCommand_exceptionThrown() {
        TangentException exception = assertThrows(TangentException.class, () -> Parser.parse("remind me"));
        assertEquals("invalid command!", exception.getMessage());
    }

    /** Verifies every invalid task-number format produces the same user-facing message. */
    private void assertInvalidTaskNumber(String input) {
        TangentException exception = assertThrows(TangentException.class, () -> Parser.parse(input));
        assertEquals("please provide a valid task number!", exception.getMessage());
    }

    /** Verifies every task-creation command requires a description. */
    private void assertMissingDescription(String input) {
        TangentException exception = assertThrows(TangentException.class, () -> Parser.parse(input));
        assertEquals("please provide a task description!", exception.getMessage());
    }
}
