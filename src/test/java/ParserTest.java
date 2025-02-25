package caviar.parser;

import caviar.command.TaskList;
import caviar.exception.CaviarException;
import caviar.storage.Storage;
import caviar.ui.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    private final TaskList taskList = new TaskList();
    private final Ui ui = new Ui();
    private final Storage storage = new Storage("data/test_tasks.txt");

    @Test
    void testParseTodo() throws CaviarException {
        Parser.parseAndExecute("todo read book", taskList, ui, storage);
        assertEquals(1, taskList.getTasks().size(), "Todo should be added.");
    }

    @Test
    void testParseInvalidCommand() {
        Exception exception = assertThrows(CaviarException.class, () ->
                Parser.parseAndExecute("unknownCommand", taskList, ui, storage)
        );
        assertTrue(exception.getMessage().contains("roe..!!"), "Should throw an error for unknown commands.");
    }
}
