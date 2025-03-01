package caviar.command;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import caviar.exception.CaviarException;
import caviar.storage.Storage;
import caviar.task.Deadline;
import caviar.task.Task;

/**
 * Represents a list of tasks that can be modified and stored.
 *
 * <p>The {@code TaskList} class manages task additions, deletions,
 * and storage operations.</p>
 */
public class TaskList {
    private ArrayList<Task> tasks;
    private Storage storage;

    /**
     * Constructs an empty {@code TaskList} without storage.
     */
    public TaskList() {
        this.storage = null; // Indicate that storage is unavailable
        this.tasks = new ArrayList<>(); // No storage dependency
    }

    /**
     * Constructs a {@code TaskList} and loads tasks from storage.
     *
     * @param storage The storage instance to load tasks from.
     * @throws IOException     If there is an issue reading from the storage file.
     * @throws CaviarException If the storage file contains invalid task data.
     */
    public TaskList(Storage storage) throws IOException, CaviarException {
        this.storage = storage;
        this.tasks = storage.load(); // Load tasks from file
    }

    /**
     * Lists all tasks in the current task list.
     * Displays a message if the task list is empty.
     */
    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("    Roe..? No tasks in the list yet.");
            return;
        }
        System.out.println("    Roe! Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("    " + (i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Saves the tasks to storage.
     *
     * @throws IOException If an error occurs during saving.
     */
    public void addTask(Task task) {
        tasks.add(task);
        System.out.println("    Roe! I've added this task:");
        System.out.println("      " + task);
        System.out.println(
            "    Now you have " + tasks.size() + " task" + (tasks.size() > 1 ? "s" : "") + " in the list. Roe roe.");

        if (storage != null) {
            try {
                storage.save(tasks);
            } catch (IOException e) {
                System.out.println("roe..!! Error saving task.");
                e.printStackTrace(); // Debugging
            }
        }
    }

    /**
     * Marks the specified task as done.
     *
     * @param index The index of the task to mark as done.
     * @throws CaviarException If the index is out of range.
     */
    public void markTask(int index) throws CaviarException {


        if (index < 0 || index >= tasks.size()) {
            throw new CaviarException("No such task exists, roe..!!");
        }
        tasks.get(index).markAsDone();
        System.out.println("    Roe! I've marked this task as done:");
        System.out.println("      " + tasks.get(index));
        try {
            saveTasks();
        } catch (IOException e) {
            System.out.println("roe..!! Error saving task.");
        }
    }

    /**
     * Marks the specified task as not done.
     *
     * <p>If the storage is available, this method also updates the tasks in the
     * storage file.</p>
     *
     * @param index The index of the task to mark as not done.
     * @throws CaviarException If the index is out of range (invalid task index).
     */
    public void unmarkTask(int index) throws CaviarException {
        if (index < 0 || index >= tasks.size()) {
            throw new CaviarException("No such task exists, roe..!!");
        }
        tasks.get(index).markAsNotDone();
        System.out.println("    Roe! I've marked this task as not done yet:");
        System.out.println("      " + tasks.get(index));
        try {
            saveTasks();
        } catch (IOException e) {
            System.out.println("roe..!! Error saving task.");
        }
    }

    /**
     * Removes a task from the list based on its index.
     *
     * <p>If the storage is available, this method also updates the tasks in the
     * storage file.</p>
     *
     * @param index The index of the task to remove.
     * @throws CaviarException If the index is out of range (invalid task index).
     */
    public void deleteTask(int index) throws CaviarException {
        if (index < 0 || index >= tasks.size()) {
            throw new CaviarException("No such task exists, roe..!!");
        }
        Task removedTask = tasks.remove(index);
        System.out.println("    Roe! I've removed this task:");
        System.out.println("      " + removedTask);
        System.out.println("    Now you have " + tasks.size() + " tasks in the list. Roe roe.");
        try {
            saveTasks();
        } catch (IOException e) {
            System.out.println("roe..!! Error saving task.");
        }
    }

    /**
     * Saves all tasks to the underlying storage.
     *
     * <p>If no storage is set (i.e., {@code storage == null}), this method does nothing.</p>
     *
     * @throws IOException If an I/O error occurs while writing tasks to the storage file.
     */
    public void saveTasks() throws IOException {
        if (storage != null) {
            storage.save(tasks);
        }
    }

    /**
     * Finds tasks that contain the specified keyword in their descriptions.
     *
     * @param keyword The keyword to search for.
     * @return A list of matching tasks.
     */
    public List<Task> findTasks(String keyword) {
        List<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }

    /**
     * Returns the list of tasks.
     *
     * @return List of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Shows all deadlines that occur on the specified date.
     *
     * <p>If no matching deadlines are found, a message is displayed instead.</p>
     *
     * @param dateStr The date string in {@code yyyy-MM-dd} format.
     */
    public void showTasksOnDate(String dateStr) {
        try {
            LocalDate targetDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            boolean found = false;
            System.out.println(
                "Roe. Deadlines for " + targetDate.format(DateTimeFormatter.ofPattern("d MMM yyyy")) + ":");
            for (Task t : tasks) {
                if (t instanceof Deadline) {
                    Deadline d = (Deadline) t;
                    if (d.getBy().toLocalDate().equals(targetDate)) {
                        System.out.println("  " + d);
                        found = true;
                    }
                }
            }
            if (!found) {
                System.out.println("No deadlines on this date.");
            }
        } catch (Exception e) {
            System.out.println("Invalid date format. Use 'yyyy-MM-dd'.");
        }
    }
}
