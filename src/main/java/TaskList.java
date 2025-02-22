import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
        System.out.println("    Roe! I've added this task:");
        System.out.println("      " + task);
        System.out.println("    Now you have " + tasks.size() + " tasks in the list. Roe roe.");
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
    }

    public void unmarkTask(int index) throws CaviarException {
        if (index < 0 || index >= tasks.size()) throw new CaviarException("No such task exists, roe..!!");
        tasks.get(index).markAsNotDone();
        System.out.println("    Roe! I've marked this task as not done yet:");
        System.out.println("      " + tasks.get(index));
    }

    public void deleteTask(int index) throws CaviarException {
        if (index < 0 || index >= tasks.size()) throw new CaviarException("No such task exists, roe..!!");
        Task removedTask = tasks.remove(index);
        System.out.println("    Roe! I've removed this task:");
        System.out.println("      " + removedTask);
        System.out.println("    Now you have " + tasks.size() + " tasks in the list. Roe roe.");
    }
}
