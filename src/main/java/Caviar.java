import java.util.Scanner;

public class Caviar {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskList taskList = new TaskList();

        System.out.println("Hello! I'm Caviar");
        System.out.println("What can I do for you?");
        System.out.println("______________________\n");

        while (true) {
            String input = scanner.nextLine();
            String[] parts = input.split(" ", 2); // Split only once to handle missing arguments

            if (input.equals("bye")) {
                System.out.println("Roe. Hope to see you again soon!");
                break;
            } else if (input.equals("list")) {
                taskList.listTasks();
            } else if (parts[0].equals("todo")) {
                if (parts.length < 2) {
                    System.out.println("roe..!! The description of a todo cannot be empty.");
                } else {
                    taskList.addTask(new Todo(parts[1]));
                }
            } else if (parts[0].equals("deadline")) {
                if (parts.length < 2 || !parts[1].contains(" /by ")) {
                    System.out.println("roe..!! The deadline command must follow this format:");
                    System.out.println("    deadline <task description> /by <due date>");
                } else {
                    String[] deadlineParts = parts[1].split(" /by ", 2); // Ensure split is done safely
                    taskList.addTask(new Deadline(deadlineParts[0], deadlineParts[1]));
                }
            } else if (parts[0].equals("event")) {
                if (parts.length < 2 || !parts[1].contains(" /from ") || !parts[1].contains(" /to ")) {
                    System.out.println("roe..!! The event command must follow this format:");
                    System.out.println("    event <task description> /from <start time> /to <end time>");
                } else {
                    String[] eventParts = parts[1].split(" /from | /to ", 3); // Ensure correct parsing
                    taskList.addTask(new Event(eventParts[0], eventParts[1], eventParts[2]));
                }
            } else {
                System.out.println("roe..? I'm sorry, but I don't know what that means.");
            }
        }

        scanner.close();
    }
}
