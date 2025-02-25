package caviar.task;

import caviar.storage.Storage;
import caviar.task.Deadline;
import caviar.task.Task;
import caviar.task.Event;
import caviar.command.TaskList;
import caviar.exception.CaviarException;

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
