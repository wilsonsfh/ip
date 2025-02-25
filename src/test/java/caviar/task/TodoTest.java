package caviar.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * JUnit test class for the {@link Todo} class.
 */
public class TodoTest {
    /**
     * Tests creation of a Todo task.
     */
    @Test
    public void testTodoCreation() {
        Todo todo = new Todo("Finish Gradle setup");
        assertEquals("[T][ ] Finish Gradle setup", todo.toString());
    }

    /**
     * Tests marking a Todo task as done.
     */
    @Test
    public void testMarkAsDone() {
        Todo todo = new Todo("Finish Gradle setup");
        todo.markAsDone();
        assertEquals("[T][X] Finish Gradle setup", todo.toString());
    }
}
