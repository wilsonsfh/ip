package caviar.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    void testMarkAsDone() {
        Task task = new Todo("Test Task");
        task.markAsDone();
        assertTrue(task.isDone, "Task should be marked as done.");
    }

    @Test
    void testMarkAsNotDone() {
        Task task = new Todo("Test Task");
        task.markAsDone();
        task.markAsNotDone();
        assertFalse(task.isDone, "Task should be marked as not done.");
    }
}
