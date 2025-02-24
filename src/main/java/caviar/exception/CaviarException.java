package caviar.exception;

import caviar.storage.Storage;
import caviar.task.Deadline;
import caviar.task.Task;
import caviar.task.Event;
import caviar.task.Todo;
import caviar.command.TaskList;

public class CaviarException extends Exception {
    public CaviarException(String message) {
        super("roe..!! " + message);
    }
}
