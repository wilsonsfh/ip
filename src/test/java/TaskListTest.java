package caviar.command;

import caviar.task.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
    }

    @Test
    void testAddTask() {
        taskList.addTask(new Todo("Test Task"));
        assertEquals(1, taskList.getTasks().size(), "Task should be added.");
    }

    @Test
    void testDeleteTask() throws Exception {
        taskList.addTask(new Todo("Task to delete"));
        taskList.deleteTask(0);
        assertEquals(0, taskList.getTasks().size(), "Task should be removed.");
    }
}
