package caviar.task;

import caviar.storage.Storage;
import caviar.task.Deadline;
import caviar.task.Event;
import caviar.task.Todo;
import caviar.command.TaskList;
import caviar.exception.CaviarException;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]");
    }

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
