package caviar.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Tests the behavior of the {@link Task} class.
 */
class TaskTest {

    /**
     * Tests if a task can be marked as done.
     */
    @Test
    void testMarkAsDone() {
        Task task = new Todo("Test Task");
        task.markAsDone();
        assertTrue(task.isDone, "Task should be marked as done.");
    }

    /**
     * Tests if a task can be marked as not done after being completed.
     */
    @Test
    void testMarkAsNotDone() {
        Task task = new Todo("Test Task");
        task.markAsDone();
        task.markAsNotDone();
        assertFalse(task.isDone, "Task should be marked as not done.");
    }
}
