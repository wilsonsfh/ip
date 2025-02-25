package caviar.parser;

import caviar.task.Task;
import caviar.task.Deadline;
import caviar.task.Todo;
import caviar.task.Event;
import caviar.command.TaskList;
import caviar.exception.CaviarException;
import caviar.storage.Storage;
import caviar.ui.Ui;
import org.junit.jupiter.api.Test;
import java.util.List;

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

    @Test
    void testParseFind() throws CaviarException {
        taskList.addTask(new Todo("read book"));
        taskList.addTask(new Deadline("return book", "2025-02-25"));

        List<Task> results = taskList.findTasks("book");
        assertEquals(2, results.size(), "Both tasks should be found.");
    }
}
