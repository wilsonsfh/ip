package caviar.parser;

import caviar.task.*;
import caviar.command.TaskList;
import caviar.exception.CaviarException;
import caviar.storage.Storage;
import caviar.ui.Ui;

/**
 * Handles parsing and execution of user commands.
 */
public class Parser {

    /**
     * Parses and executes the user input.
     *
     * @param input     The user input command.
     * @param taskList  The list of tasks.
     * @param ui        The UI instance for interaction.
     * @param storage   The storage handler.
     * @throws CaviarException if the command is invalid.
     */
    public static void parseAndExecute(String input, TaskList taskList, Ui ui, Storage storage) throws CaviarException {
        String[] parts = input.split(" ", 2);
        switch (parts[0]) {
            case "bye":
                ui.showMessage("Roe. Hope to see you again soon!");
                System.exit(0);
                break;
            case "list":
                taskList.listTasks();
                break;
            case "todo":
                if (parts.length < 2) throw new CaviarException("The description of a todo cannot be empty.");
                taskList.addTask(new Todo(parts[1]));
                break;
            case "deadline":
                if (parts.length < 2 || !parts[1].contains(" /by "))
                    throw new CaviarException("The deadline format is incorrect! Use: deadline <task> /by <date>.");
                String[] deadlineParts = parts[1].split(" /by ", 2);
                taskList.addTask(new Deadline(deadlineParts[0], deadlineParts[1]));
                break;
            case "event":
                if (parts.length < 2 || !parts[1].contains(" /from ") || !parts[1].contains(" /to "))
                    throw new CaviarException("The event format is incorrect! Use: event <task> /from <start> /to <end>.");
                String[] eventParts = parts[1].split(" /from | /to ", 3);
                taskList.addTask(new Event(eventParts[0], eventParts[1], eventParts[2]));
                break;
            case "mark":
                if (parts.length < 2) throw new CaviarException("Mark which task? roe..!!");
                int markIndex = Integer.parseInt(parts[1]) - 1;
                taskList.markTask(markIndex);
                break;
            case "unmark":
                if (parts.length < 2) throw new CaviarException("Unmark which task? roe..!!");
                int unmarkIndex = Integer.parseInt(parts[1]) - 1;
                taskList.unmarkTask(unmarkIndex);
                break;
            case "delete":
                if (parts.length < 2) throw new CaviarException("Delete which task? roe..!!");
                int deleteIndex = Integer.parseInt(parts[1]) - 1;
                taskList.deleteTask(deleteIndex);
                break;
            case "find":
                if (parts.length < 2) throw new CaviarException("Find what? Roe..!!");
                taskList.findTasks(parts[1]);
                break;
            default:
                throw new CaviarException("I don't understand roe..?");
        }

        // Save after every change
        try {
            storage.save(taskList.getTasks());
        } catch (Exception e) {
            ui.showMessage("roe..!! Error saving task.");
        }
    }
}
