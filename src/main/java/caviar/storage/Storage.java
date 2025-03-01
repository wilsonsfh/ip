package caviar.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import caviar.exception.CaviarException;
import caviar.task.Task;

/**
 * Manages storage of tasks by saving and loading them to/from a file.
 *
 * <p>The {@code Storage} class provides methods to persist a list of tasks to a file
 * and retrieve them when needed.</p>
 */
public class Storage {
    private String filePath;

    /**
     * Constructs a {@code Storage} instance with the specified file path.
     *
     * @param filePath The path of the file where tasks are stored.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves the list of tasks to the storage file.
     *
     * <p>If the storage file's directory does not exist, it is created automatically.</p>
     *
     * @param tasks The list of tasks to save.
     * @throws IOException If an error occurs while writing to the file.
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        File file = new File(filePath);
        createDirectoryIfMissing(file);

        FileWriter writer = new FileWriter(file);
        for (Task task : tasks) {
            writer.write(task.toStorageString() + "\n");
        }
        writer.close();
    }

    private void createDirectoryIfMissing(File file) {
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
            System.out.println("Roe..!! Created missing directory for tasks.");
        }
    }

    /**
     * Loads tasks from the storage file.
     *
     * @return The list of loaded tasks.
     * @throws IOException     If an error occurs while reading the file.
     * @throws CaviarException If an error occurs while parsing task data.
     */
    public ArrayList<Task> load() throws IOException, CaviarException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return tasks;
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
