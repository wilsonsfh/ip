package caviar.task;

import caviar.storage.Storage;
import caviar.task.Deadline;
import caviar.task.Task;
import caviar.task.Event;
import caviar.command.TaskList;
import caviar.exception.CaviarException;

public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
