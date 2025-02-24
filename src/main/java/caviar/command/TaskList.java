package caviar.command;

import caviar.storage.Storage;
import caviar.task.Deadline;
import caviar.task.Task;
import caviar.task.Event;
import caviar.task.Todo;
import caviar.exception.CaviarException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.io.IOException;

public class TaskList {
    private ArrayList<Task> tasks;
    private Storage storage;

    public TaskList() {
        this.storage = null; // Indicate that storage is unavailable
        this.tasks = new ArrayList<>(); // No storage dependency
    }

    public TaskList(Storage storage) throws IOException, CaviarException {
        this.storage = storage;
        this.tasks = storage.load(); // Load tasks from file
    }

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

    public void addTask(Task task) {
        tasks.add(task);
        System.out.println("    Roe! I've added this task:");
        System.out.println("      " + task);
        System.out.println("    Now you have " + tasks.size() + " task" + (tasks.size() > 1 ? "s" : "") + " in the list. Roe roe.");

        if (storage != null) {
            try {
                storage.save(tasks);
            } catch (IOException e) {
                System.out.println("roe..!! Error saving task.");
                e.printStackTrace(); // Debugging
            }
        }
    }

    public void markTask(int index) throws CaviarException {
        if (index < 0 || index >= tasks.size()) throw new CaviarException("No such task exists, roe..!!");
        tasks.get(index).markAsDone();
        System.out.println("    Roe! I've marked this task as done:");
        System.out.println("      " + tasks.get(index));
        try {
            saveTasks();
        } catch (IOException e) {
            System.out.println("roe..!! Error saving task.");
        }
    }

    public void unmarkTask(int index) throws CaviarException {
        if (index < 0 || index >= tasks.size()) throw new CaviarException("No such task exists, roe..!!");
        tasks.get(index).markAsNotDone();
        System.out.println("    Roe! I've marked this task as not done yet:");
        System.out.println("      " + tasks.get(index));
        try {
            saveTasks();
        } catch (IOException e) {
            System.out.println("roe..!! Error saving task.");
        }
    }

    public void deleteTask(int index) throws CaviarException {
        if (index < 0 || index >= tasks.size()) throw new CaviarException("No such task exists, roe..!!");
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

    public void saveTasks() throws IOException {
        if (storage != null) {
            storage.save(tasks);
        }
    }

    /**
     * Finds and prints tasks that contain the given keyword.
     *
     * @param keyword The keyword to search for.
     */
    public void findTasks(String keyword) {
        System.out.println("    Roe! Here are the matching tasks in your list:");
        int count = 1;
        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                System.out.println("    " + count + "." + task);
                count++;
            }
        }
        if (count == 1) {
            System.out.println("    Roe..? No matching tasks found.");
        }
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
     * Shows all deadlines that fall on the specified date.
     */
    public void showTasksOnDate(String dateStr) {
        try {
            LocalDate targetDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            boolean found = false;
            System.out.println("Roe. Deadlines for " + targetDate.format(DateTimeFormatter.ofPattern("d MMM yyyy")) + ":");
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
