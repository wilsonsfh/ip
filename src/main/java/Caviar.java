import java.util.Scanner;
import java.io.IOException;

public class Caviar {
    public static void main(String[] args) throws IOException, CaviarException {
        Scanner scanner = new Scanner(System.in);
        Storage storage = new Storage("data/tasks.txt");
        TaskList taskList;

        try {
            taskList = new TaskList(storage); // Try loading tasks from storage
        } catch (IOException e) {
            System.out.println("Roe..? Error loading tasks.");
            taskList = new TaskList(); // Fallback TaskList with no storage dependency
        }

        System.out.println("Hello! I'm Caviar. Roe!");
        System.out.println("What can I do for you?");
        System.out.println("______________________\n");

        while (true) {
            try {
                String input = scanner.nextLine().trim();
                if (input.equals("bye")) {
                    System.out.println("Roe. Hope to see you again soon!");
                    break;
                } else if (input.equals("list")) {
                    taskList.listTasks();
                } else {
                    String[] parts = input.split(" ", 2);
                    if (parts[0].equals("todo")) {
                        if (parts.length < 2) throw new CaviarException("The description of a todo cannot be empty.");
                        taskList.addTask(new Todo(parts[1]));
                    } else if (parts[0].equals("deadline")) {
                        if (parts.length < 2 || !parts[1].contains(" /by ")) throw new CaviarException("The deadline format is incorrect! Use: deadline <task> /by <date>.");
                        String[] deadlineParts = parts[1].split(" /by ", 2);
                        try {
                            taskList.addTask(new Deadline(deadlineParts[0], deadlineParts[1]));
                        } catch (CaviarException e) {
                            System.out.println(e.getMessage());
                        }
                    } else if (parts[0].equals("event")) {
                        if (parts.length < 2 || !parts[1].contains(" /from ") || !parts[1].contains(" /to ")) throw new CaviarException("The event format is incorrect! Use: event <task> /from <start> /to <end>.");
                        String[] eventParts = parts[1].split(" /from | /to ", 3);
                        taskList.addTask(new Event(eventParts[0], eventParts[1], eventParts[2]));
                    } else if (parts[0].equals("on")) {
                        taskList.showTasksOnDate(parts[1]);
                    } else if (parts[0].equals("mark")) {
                        if (parts.length < 2) throw new CaviarException("Mark which task? roe..!!");
                        int index = Integer.parseInt(parts[1]) - 1;
                        taskList.markTask(index);
                    } else if (parts[0].equals("unmark")) {
                        if (parts.length < 2) throw new CaviarException("Unmark which task? roe..!!");
                        int index = Integer.parseInt(parts[1]) - 1;
                        taskList.unmarkTask(index);
                    } else if (parts[0].equals("delete")) {
                        if (parts.length < 2) throw new CaviarException("Delete which task? roe..!!");
                        int index = Integer.parseInt(parts[1]) - 1;
                        taskList.deleteTask(index);
                    } else {
                        throw new CaviarException("I don't understand roe..?");
                    }
                }
            } catch (CaviarException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("roe..!! Invalid number format.");
            } catch (Exception e) {
                System.out.println("roe..!! Something went wrong: " + e.getMessage());
                e.printStackTrace(); // ✅ Print stack trace for debugging
            }
        }

        scanner.close();
    }
}
