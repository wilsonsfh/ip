import java.util.ArrayList;
import java.io.IOException;

public class TaskList {
    private ArrayList<Task> tasks;
    private Storage storage;

    public TaskList() {
        this.tasks = new ArrayList<>(); // No storage dependency
        this.storage = null; // Indicate that storage is unavailable
    }

    public TaskList(Storage storage) throws IOException {
        this.storage = storage;
        this.tasks = storage.load(); // Load tasks from file
    }

    public void saveTasks() throws IOException {
        if (storage != null) {
            storage.save(tasks);
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
            }
        }
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
}
