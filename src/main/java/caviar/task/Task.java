package caviar.task;

import caviar.storage.Storage;
import caviar.task.Deadline;
import caviar.task.Event;
import caviar.task.Todo;
import caviar.command.TaskList;
import caviar.exception.CaviarException;

/**
 * Represents a generic task in the task list.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new Task.
     *
     * @param description The task description.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the task description.
     *
     * @return The task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Returns the status icon for the task.
     *
     * @return [X] if done, [ ] if not done.
     */
    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]");
    }

    /**
     * Converts the task into a storage-friendly string format.
     *
     * @return The formatted storage string.
     */
    public String toStorageString() {
        return (this instanceof Todo ? "T" : this instanceof Deadline ? "D" : "E")
                + " | " + (isDone ? "1" : "0") + " | " + description;
    }

    @Override
    public String toString() {
        return getStatusIcon() + " " + description;
    }

    public static Task fromStorageString(String data) throws CaviarException {
        String[] parts = data.split(" \\| ");
        String type = parts[0]; // T, D, or E
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;
        try {
            switch (type) {
                case "T":
                    task = new Todo(description);
                    break;
                case "D":
                    if (parts.length < 4) {
                        throw new CaviarException("Invalid deadline format in storage, roe..!!");
                    }
                    String by = parts[3]; // Get the deadline time string
                    task = new Deadline(description, by);
                    break;
                case "E":
                    if (parts.length < 5) {
                        throw new CaviarException("Invalid event format in storage, roe..!!");
                    }
                    String from = parts[3];
                    String to = parts[4];
                    task = new Event(description, from, to);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid task type in storage: " + type);
            }

            if (isDone) {
                task.markAsDone();
            }

            return task;
        } catch (CaviarException e) {
            System.out.println("roe..!! Error loading task from storage: " + data);
            e.printStackTrace();
        }

        return null;
    }
}
