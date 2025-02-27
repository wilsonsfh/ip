package caviar.task;

/**
 * Represents a Todo task, which is a simple task without any date or time.
 */
public class Todo extends Task {

    /**
     * Constructs a new Todo task.
     *
     * @param description The task description.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the Todo task.
     *
     * @return The formatted task string.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
