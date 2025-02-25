package caviar.storage;

import caviar.task.Deadline;
import caviar.task.Task;
import caviar.task.Event;
import caviar.task.Todo;
import caviar.command.TaskList;
import caviar.exception.CaviarException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void save(ArrayList<Task> tasks) throws IOException {
        File file = new File(filePath);
        File parentDir = file.getParentFile(); // Ensure the directory exists

        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs(); // Create missing folders
            System.out.println("Roe..!! Created missing directory for tasks.");

        }

        FileWriter writer = new FileWriter(file);
        for (Task task : tasks) {
            writer.write(task.toStorageString() + "\n");
        }
        writer.close();
    }

    public ArrayList<Task> load() throws IOException, CaviarException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return tasks; // Return empty list if file does not exist
        }

        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            tasks.add(Task.fromStorageString(line));
        }
        scanner.close();
        return tasks;
    }
}
