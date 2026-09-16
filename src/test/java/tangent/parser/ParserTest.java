package tangent.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import tangent.command.AddCommand;
import tangent.command.DeleteCommand;
import tangent.command.ExitCommand;
import tangent.command.FindCommand;
import tangent.command.ListCommand;
import tangent.command.MarkCommand;
import tangent.command.UnmarkCommand;
import tangent.exception.ErrorMessages;
import tangent.exception.TangentException;



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
    public void parseTaskNumbers_validIndexes_correctIndexListReturned() throws TangentException {
        List<Integer> indexes = Parser.parseTaskIndexes(new String[]{"delete", "1 2-4 6-8"});

        assertEquals(List.of(0, 1, 2, 3, 5, 6, 7), indexes);
    }

    @Test
    public void parseTaskNumbers_invalidSyntax_exceptionWithHelpThrown() {
        assertInvalidSelectorFormat("delete 2 - 5");
        assertInvalidSelectorFormat("delete 02-05");
        assertInvalidSelectorFormat("delete 2--5");
        assertInvalidSelectorFormat("delete 2-");
        assertInvalidSelectorFormat("delete 1,3");
        assertInvalidTaskNumber("delete -5");
    }

    @Test
    public void parseTaskNumbers_reversedRangeOrDuplicateIndex_exceptionThrown() {
        assertInvalidTaskNumber("delete 5-2");
        assertInvalidTaskNumber("delete 2 2");
        assertInvalidTaskNumber("delete 1-3 3-5");
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
        assertEquals(ErrorMessages.MISSING_SEARCH_KEYWORD_MESSAGE, exception.getMessage());
    }

    @Test
    public void parse_unknownCommand_exceptionThrown() {
        TangentException exception = assertThrows(TangentException.class, () -> Parser.parse("remind me"));
        assertEquals(ErrorMessages.INVALID_COMMAND_MESSAGE, exception.getMessage());
    }

    /** Verifies every invalid task-number format produces the same user-facing message. */
    private void assertInvalidTaskNumber(String input) {
        TangentException exception = assertThrows(TangentException.class, () -> Parser.parse(input));
        assertEquals(ErrorMessages.INVALID_TASK_INDEX_MESSAGE, exception.getMessage());
    }

    /** Verifies every task-creation command requires a description. */
    private void assertMissingDescription(String input) {
        TangentException exception = assertThrows(TangentException.class, () -> Parser.parse(input));
        assertEquals(ErrorMessages.MISSING_TASK_DESCRIPTION_MESSAGE, exception.getMessage());
    }

    /** Verifies malformed selectors produce the selector-format help message. */
    private void assertInvalidSelectorFormat(String input) {
        TangentException exception = assertThrows(TangentException.class, () -> Parser.parse(input));
        assertEquals(ErrorMessages.TASK_INDEX_FORMAT_MESSAGE,
                exception.getMessage());
    }
}
