import caviar.task.Todo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {
    @Test
    public void testTodoCreation() {
        Todo todo = new Todo("Finish Gradle setup");
        assertEquals("[T][ ] Finish Gradle setup", todo.toString());
    }

    @Test
    public void testMarkAsDone() {
        Todo todo = new Todo("Finish Gradle setup");
        todo.markAsDone();
        assertEquals("[T][X] Finish Gradle setup", todo.toString());
    }
}
