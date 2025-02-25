package caviar;

import caviar.storage.Storage;
import caviar.task.*;
import caviar.command.TaskList;
import caviar.parser.Parser;
import caviar.exception.CaviarException;
import caviar.ui.Ui;

import java.io.IOException;

public class Caviar {
    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;

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
        Parser.parseAndExecute(input, taskList, ui, storage);
    }


    public static void main(String[] args) {
        try {
            new Caviar("data/tasks.txt").run();
        } catch (CaviarException e) {
        }
    }
}
