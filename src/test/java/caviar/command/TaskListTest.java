package caviar.command;

import caviar.task.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
