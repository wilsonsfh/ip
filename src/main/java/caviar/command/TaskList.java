package caviar.command;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import caviar.exception.CaviarException;
import caviar.storage.Storage;
import caviar.task.Deadline;
import caviar.task.Event;
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
     * Validates the index for mark/unmark/delete.
     */
    private void validateIndex(int index) throws CaviarException {
        if (index < 0 || index >= tasks.size()) {
            throw new CaviarException("No such task exists, roe..!!");
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
        if (storage == null) {
            return;
        }
        try {
            storage.save(tasks);
        } catch (IOException e) {
            System.out.println("roe..!! Error saving task.");
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
     * Sorts tasks by a user-chosen option:
     *   1 = chronologically, A→Z
     *   2 = reverse, Z→A
     */
    public void sortTasksByOption(int option) {
        if (option != 1 && option != 2) {
            System.out.println("Invalid sort option. Please enter 1 or 2.");
            return;
        }

        // User want chronological order with option 1
        Comparator<Task> baseComparator = buildBaseComparator();

        // User want reversed chronological order with option 2
        if (option == 2) {
            baseComparator = baseComparator.reversed();
        }

        tasks.sort(baseComparator);
        printSortedTasks();
    }

    private LocalDateTime getDateTimeIfAny(Task t) {
        if (t instanceof Deadline) {
            return ((Deadline) t).getBy();
        } else if (t instanceof Event) {
            return ((Event) t).getFrom();
        }
        return null; // Todo has no date/time
    }

    private Comparator<Task> buildBaseComparator() {
        return (t1, t2) -> {
            LocalDateTime dt1 = getDateTimeIfAny(t1);
            LocalDateTime dt2 = getDateTimeIfAny(t2);

            // Compare both tasks that has date/time
            if (dt1 != null && dt2 != null) {
                return dt1.compareTo(dt2);
            }
            // One task out of the two has date/time, then it goes first
            if (dt1 != null && dt2 == null) {
                return -1;
            }
            if (dt1 == null && dt2 != null) {
                return 1;
            }

            // Default, compare by description
            return t1.getDescription().compareToIgnoreCase(t2.getDescription());
        };
    }

    /**
     * Sorts tasks based on their type and a specified sorting option.
     *
     * <p>This method filters tasks by the given type ('todo', 'deadline', or 'event') and sorts them
     * according to the specified option. The sorting options are:
     * <ul>
     *   <li>1: Sort in ascending order.</li>
     *   <li>2: Sort in descending order.</li>
     * </ul>
     * If the task type is invalid or the sorting option is neither 1 nor 2, an appropriate message is displayed.</p>
     *
     * @param type   The type of tasks to sort ('todo', 'deadline', or 'event').
     * @param option The sorting option: 1 for ascending, 2 for descending.
     */
    public void sortTasksByType(String type, int option) {
        if (!isValidType(type)) {
            System.out.println("Unknown task type. Use 'todo', 'deadline', or 'event'.");
            return;
        }
        if (option != 1 && option != 2) {
            System.out.println("Invalid sort option. Please enter 1 or 2.");
            return;
        }

        // Store tasks of the chosen type
        List<Task> filteredTasks = filterTasksByType(type);

        // Sort tasks using the same base comparator
        Comparator<Task> baseComparator = buildBaseComparator();
        if (option == 2) {
            baseComparator = baseComparator.reversed();
        }
        filteredTasks.sort(baseComparator);

        printFilteredTasks(filteredTasks, type, option);
    }

    private boolean isValidType(String type) {
        return type.equals("todo") || type.equals("deadline") || type.equals("event");
    }

    private List<Task> filterTasksByType(String type) {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (type.equals("todo") && t instanceof caviar.task.Todo) {
                result.add(t);
            } else if (type.equals("deadline") && t instanceof caviar.task.Deadline) {
                result.add(t);
            } else if (type.equals("event") && t instanceof caviar.task.Event) {
                result.add(t);
            }
        }
        return result;
    }

    private void printFilteredTasks(List<Task> filtered, String type, int option) {
        if (filtered.isEmpty()) {
            System.out.println("No " + type + " tasks found to sort.");
            return;
        }
        String orderName = (option == 1) ? "chronologically, A→Z" : "reverse, Z→A";
        System.out.println("Here are your " + type + " tasks sorted (" + orderName + "):");
        for (int i = 0; i < filtered.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + filtered.get(i));
        }
    }
    private void printSortedTasks() {
        System.out.println("Here are your tasks after sorting:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + tasks.get(i));
        }
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
