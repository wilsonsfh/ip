package caviar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import caviar.command.TaskList;
import caviar.exception.CaviarException;
import caviar.parser.Parser;
import caviar.storage.Storage;
import caviar.ui.Ui;

/**
 * The main class for the Caviar chatbot application.
 *
 * <p>Caviar is an interactive task manager that allows users to add,
 * manage, and store tasks with deadlines and events.</p>
 */
public class Caviar {
    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;

    /**
     * Initializes the chatbot, loading tasks from storage if available.
     *
     * @param filePath The path to the storage file.
     * @throws CaviarException If an error occurs while loading tasks.
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
     * Captures the output from parseAndExecute so it can be displayed in the GUI.
     *
     * @param input The user command
     * @return The output text from parseAndExecute
     */
    public String getResponseFromCaviar(String input) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(ps);

        try {
            Parser.parseAndExecute(input, taskList, ui, storage);
        } catch (CaviarException e) {
            return e.getMessage();
        } finally {
            System.setOut(oldOut);
        }

        return baos.toString();
    }

    /**
     * Runs the main interaction loop of CLI version for Caviar chatbot.
     *
     * <p>The chatbot continuously waits for user input and processes commands
     * until the user inputs "bye".</p>
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

    /**
     * Processes a user command and executes the corresponding action.
     *
     * @param input The command input from the user.
     * @throws CaviarException If the command is invalid.
     */
    private void processCommand(String input) throws CaviarException {
        Parser.parseAndExecute(input, taskList, ui, storage);
    }

    /**
     * The main entry point of the Caviar application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        try {
            new Caviar("data/tasks.txt").run();
        } catch (CaviarException e) {
            e.printStackTrace();
        }
    }
}
