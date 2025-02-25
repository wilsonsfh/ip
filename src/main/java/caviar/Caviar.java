package caviar;

import caviar.storage.Storage;
import caviar.task.*;
import caviar.command.TaskList;
import caviar.exception.CaviarException;
import caviar.ui.Ui;
import java.io.IOException;

/**
 * The main class for the Caviar chatbot application.
 * It talks to you, and acts as a interactive to-do list friend for you.
 */
public class Caviar {
    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;

    /**
     * Initializes the chatbot, loading tasks from storage if available.
     *
     * @param filePath Path to the storage file.
     */
    public Caviar(String filePath) throws CaviarException {
        ui = new Ui();
        storage = new Storage(filePath);
        TaskList tempTaskList;
        try {
            tempTaskList = new TaskList(storage);
        } catch (IOException e) {
            ui.showMessage("Roe..? Error loading tasks.");
            tempTaskList = new TaskList();
        }
        taskList = tempTaskList;
    }

    /**
     * Runs the main interaction loop of Caviar chatbot.
     */
    public void run() {
        ui.showWelcome();
        while (true) {
            try {
                String input = ui.readCommand();
                if (input.equals("bye")) {
                    ui.showMessage("Roe. Hope to see you again soon!");
                    break;
                } else if (input.equals("list")) {
                    taskList.listTasks();
                } else {
                    processCommand(input);
                }
            } catch (CaviarException e) {
                ui.showMessage(e.getMessage());
            } catch (NumberFormatException e) {
                ui.showMessage("roe..!! Invalid number format.");
            } catch (Exception e) {
                ui.showMessage("roe..!! Something went wrong.");
            }
        }
        ui.close();
    }

    private void processCommand(String input) throws CaviarException {
        String[] parts = input.split(" ", 2);
        if (parts[0].equals("todo")) {
            if (parts.length < 2) throw new CaviarException("The description of a todo cannot be empty.");
            taskList.addTask(new Todo(parts[1]));
        } else if (parts[0].equals("deadline")) {
            if (parts.length < 2 || !parts[1].contains(" /by "))
                throw new CaviarException("The deadline format is incorrect! Use: deadline <task> /by <date>.");
            String[] deadlineParts = parts[1].split(" /by ", 2);
            taskList.addTask(new Deadline(deadlineParts[0], deadlineParts[1]));
        } else if (parts[0].equals("event")) {
            if (parts.length < 2 || !parts[1].contains(" /from ") || !parts[1].contains(" /to "))
                throw new CaviarException("The event format is incorrect! Use: event <task> /from <start> /to <end>.");
            String[] eventParts = parts[1].split(" /from | /to ", 3);
            taskList.addTask(new Event(eventParts[0], eventParts[1], eventParts[2]));
        } else {
            throw new CaviarException("I don't understand roe..?");
        }
    }

    /**
     * The main entry point of the application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        try {
            new Caviar("data/tasks.txt").run();
        } catch (CaviarException e) {
        }
    }
}
