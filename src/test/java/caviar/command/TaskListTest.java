package caviar.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import caviar.task.Task;
import caviar.task.Todo;

/**
 * Tests the behavior of the {@link TaskList} class.
 */
class TaskListTest {
    private TaskList taskList;

    /**
     * Initializes a new {@link TaskList} before each test.
     */
    @BeforeEach
    void setUp() {
        taskList = new TaskList();
    }

    /**
     * Tests if a task can be added to the task list.
     */
    @Test
    void testFindTasks() {
        taskList.getTasks().add(new Todo("Read book"));
        taskList.getTasks().add(new Todo("Return book to library"));
        taskList.getTasks().add(new Todo("Go for a run"));
        List<Task> results = taskList.findTasks("book");
        assertEquals(2, results.size(), "Should return 2 matching tasks.");
        assertEquals("Read book", results.get(0).getDescription());
        assertEquals("Return book to library", results.get(1).getDescription());
    }

    @Test
    void testAddTask() {
        taskList.addTask(new Todo("Test Task"));
        assertEquals(1, taskList.getTasks().size(), "Task should be added.");
    }

    /**
     * Tests if a task can be deleted from the task list.
     *
     * @throws Exception If deletion fails.
     */
    @Test
    void testDeleteTask() throws Exception {
        taskList.addTask(new Todo("Task to delete"));
        taskList.deleteTask(0);
        assertEquals(0, taskList.getTasks().size(), "Task should be removed.");
    }
}
