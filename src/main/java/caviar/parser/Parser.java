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
        case "mark":
            handleMark(arguments, taskList);
            break;
        case "unmark":
            handleUnmark(arguments, taskList);
            break;
        case "todo":
            handleTodo(arguments, taskList);
            break;
        case "deadline": // NEW
            handleDeadline(arguments, taskList);
            break;
        case "event": // NEW
            handleEvent(arguments, taskList);
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
        case "sort":
            handleSort(arguments, taskList);
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

    private static void handleMark(String arguments, TaskList taskList) throws CaviarException {
        if (arguments.isEmpty()) {
            throw new CaviarException("Please specify the task number to mark.");
        }
        try {
            int index = Integer.parseInt(arguments);
            taskList.markTask(index - 1); // Convert 1-based user input to 0-based index
        } catch (NumberFormatException e) {
            throw new CaviarException("Invalid task number format.");
        }
    }

    private static void handleUnmark(String arguments, TaskList taskList) throws CaviarException {
        if (arguments.isEmpty()) {
            throw new CaviarException("Please specify the task number to unmark.");
        }
        try {
            int index = Integer.parseInt(arguments);
            taskList.unmarkTask(index - 1); // Convert 1-based user input to 0-based index
        } catch (NumberFormatException e) {
            throw new CaviarException("Invalid task number format.");
        }
    }

    private static void handleTodo(String arguments, TaskList taskList) throws CaviarException {
        if (arguments.trim().isEmpty()) {
            throw new CaviarException("The description of a todo cannot be empty.");
        }
        taskList.addTask(new Todo(arguments));
    }

    private static void handleDeadline(String arguments, TaskList taskList) throws CaviarException {
        if (arguments.isEmpty()) {
            throw new CaviarException("The description of a deadline cannot be empty.");
        }

        // e.g., "return book /by 2025-02-18 1800"
        String[] parts = arguments.split(" /by ", 2);
        if (parts.length < 2) {
            throw new CaviarException("Please specify '/by' for deadline. Example:\n"
                + "  deadline return book /by 2025-02-18 1800");
        }

        String description = parts[0].trim();
        String by = parts[1].trim();
        taskList.addTask(new Deadline(description, by));
    }

    private static void handleEvent(String arguments, TaskList taskList) throws CaviarException {
        if (arguments.isEmpty()) {
            throw new CaviarException("The description of an event cannot be empty.");
        }

        // e.g., "project meeting /from 2025-03-01 10:00 /to 2025-03-01 12:00"
        String[] fromParts = arguments.split(" /from ", 2);
        if (fromParts.length < 2) {
            throw new CaviarException("Please specify '/from' for event. Example:\n"
                + "  event project meeting /from 2025-03-01 10:00 /to 2025-03-01 12:00");
        }

        String description = fromParts[0].trim();
        String[] toParts = fromParts[1].split(" /to ", 2);
        if (toParts.length < 2) {
            throw new CaviarException("Please specify '/to' for event. Example:\n"
                + "  event project meeting /from 2025-03-01 10:00 /to 2025-03-01 12:00");
        }

        String from = toParts[0].trim();
        String to = toParts[1].trim();
        taskList.addTask(new Event(description, from, to));
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

    private static void handleSort(String arguments, TaskList taskList) {
        if (arguments.isEmpty()) {
            printSortOptions();
            return;
        }

        String[] parts = arguments.split("\\s+", 2);
        // If user typed only one token (e.g. "1" or "2"), do the existing "sort all" approach
        if (parts.length == 1) {
            handleSortAll(parts[0], taskList);
            return;
        }

        // Otherwise, user typed two tokens: e.g. "deadline 1"
        String type = parts[0].toLowerCase();
        String optionStr = parts[1];

        try {
            int option = Integer.parseInt(optionStr);
            taskList.sortTasksByType(type, option);
        } catch (NumberFormatException e) {
            System.out.println("Invalid sort option. Please enter 1 or 2.");
        }
    }

    private static void handleSortAll(String optionStr, TaskList taskList) {
        try {
            int option = Integer.parseInt(optionStr);
            taskList.sortTasksByOption(option);
        } catch (NumberFormatException e) {
            System.out.println("Invalid sort option. Please enter either\n"
                + "sort 1\n or"
                + "sort 2");
        }
    }

    private static void printSortOptions() {
        System.out.println("Sort options:\n"
            + "Sort all\n"
            + " i.e. sort 1\n"
            + "      sort 2\n"
            + "Or sort a specific type:\n"
            + "  sort todo 1\n"
            + "  sort deadline 2\n"
            + "  sort event 1");
    }

    private static void saveData(TaskList taskList, Storage storage, Ui ui) {
        try {
            storage.save(taskList.getTasks());
        } catch (Exception e) {
            ui.showMessage("roe..!! Error saving task.");
        }
    }
}
