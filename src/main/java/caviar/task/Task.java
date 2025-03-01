package caviar.task;

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
        return isDone ? "[X]" : "[ ]";
    }

    /**
     * Converts the task into a storage-friendly string format.
     *
     * @return The formatted storage string.
     */
    public String toStorageString() {
        String taskType;
        if (this instanceof Todo) {
            taskType = "T";
        } else if (this instanceof Deadline) {
            taskType = "D";
        } else {
            taskType = "E";
        }

        String status = isDone ? "1" : "0";

        return taskType + " | " + status + " | " + description;
    }

    @Override
    public String toString() {
        return getStatusIcon() + " " + description;
    }

    /**
     * Recreates a Task object from a storage string.
     *
     * @param data The storage string containing task data.
     * @return The reconstructed Task object.
     * @throws CaviarException If the data is invalid.
     */
    public static Task fromStorageString(String data) throws CaviarException {
        String[] parts = data.split(" \\| ");
        Task task = createTask(parts);
        if (parts[1].equals("1")) {
            task.markAsDone();
        }
        return task;
    }

    private static Task createTask(String[] parts) throws CaviarException {
        String type = parts[0], description = parts[2];
        if ("T".equals(type)) return new Todo(description);
        if ("D".equals(type)) {
            if (parts.length < 4)
                throw new CaviarException("Invalid deadline format in storage, roe..!!");
            return new Deadline(description, parts[3]);
        }
        if ("E".equals(type)) {
            if (parts.length < 5)
                throw new CaviarException("Invalid event format in storage, roe..!!");
            return new Event(description, parts[3], parts[4]);
        }
        throw new IllegalArgumentException("Invalid task type in storage: " + type);
    }

}
