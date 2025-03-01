package caviar.parser;

import caviar.command.TaskList;
import caviar.exception.CaviarException;
import caviar.storage.Storage;
import caviar.task.Deadline;
import caviar.task.Event;
import caviar.task.Task;
import caviar.task.Todo;
import caviar.ui.Ui;

/**
 * Handles parsing and execution of corresponding command.
 */
public class Parser {

    public static void parseAndExecute(String input, TaskList taskList, Ui ui, Storage storage) throws CaviarException {
        assert input != null : "Command input cannot be null";
        assert taskList != null : "TaskList cannot be null";
        assert ui != null : "Ui cannot be null";
        assert storage != null : "Storage cannot be null";

        String[] parts = input.split(" ", 2);
        String command = parts[0];
        String arguments = (parts.length > 1) ? parts[1] : "";

        switch (command) {
        case "bye":
            handleBye(ui);
            System.exit(0);
            break;
        case "list":
            handleList(taskList);
            break;
        case "todo":
            handleTodo(arguments, taskList);
            break;
        case "delete":
            handleDelete(arguments, taskList);
            break;
        case "find":
            handleFind(arguments, taskList);
            break;
        case "date":
            handleDate(arguments, taskList);
            break;
        default:
            throw new CaviarException("I don't understand roe..?");
        }
        saveData(taskList, storage, ui);
    }

    private static String handleBye(Ui ui) {
        ui.showMessage("Roe. Hope to see you again soon!");
        return "bye";
    }

    private static void handleList(TaskList taskList) {
        taskList.listTasks();
    }

    private static void handleTodo(String arguments, TaskList taskList) throws CaviarException {
        if (arguments.trim().isEmpty()) {
            throw new CaviarException("The description of a todo cannot be empty.");
        }
        taskList.addTask(new Todo(arguments));
    }

    private static void handleDelete(String arguments, TaskList taskList) throws CaviarException {
        if (arguments.trim().isEmpty()) {
            throw new CaviarException("Please specify the task number to delete.");
        }
        try {
            int index = Integer.parseInt(arguments.trim());
            taskList.deleteTask(index - 1);
        } catch (NumberFormatException e) {
            throw new CaviarException("Invalid task number format.");
        }
    }

    private static void handleFind(String arguments, TaskList taskList) {
        if (arguments.trim().isEmpty()) {
            System.out.println("Please specify a keyword to find tasks.");
            return;
        }
        java.util.List<Task> matchingTasks = taskList.findTasks(arguments.trim());
        if (matchingTasks.isEmpty()) {
            System.out.println("No matching tasks found.");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < matchingTasks.size(); i++) {
                System.out.println((i + 1) + ". " + matchingTasks.get(i));
            }
        }
    }

    private static void handleDate(String arguments, TaskList taskList) {
        if (arguments.trim().isEmpty()) {
            System.out.println("Please specify a date in yyyy-MM-dd format.");
            return;
        }
        taskList.showTasksOnDate(arguments.trim());
    }

    private static void saveData(TaskList taskList, Storage storage, Ui ui) {
        try {
            storage.save(taskList.getTasks());
        } catch (Exception e) {
            ui.showMessage("roe..!! Error saving task.");
        }
    }
}
