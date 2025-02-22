import java.util.Scanner;

public class Caviar {
    private TaskList taskList;
    private Scanner scanner;

    public Caviar() {
        taskList = new TaskList();
        scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("Hello! I'm Caviar");
        System.out.println("What can I do for you?");
        System.out.println("______________________");

        boolean isRunning = true;
        while (isRunning) {
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println("Roe. Hope to see you again soon!");
                isRunning = false;
            } else if (input.equalsIgnoreCase("list")) {
                taskList.listAllTasks();
            } else if (input.startsWith("mark ")) {
                try {
                    int index = Integer.parseInt(input.substring(5).trim()) - 1;
                    taskList.markTask(index);
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + taskList.getTask(index));
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    System.out.println("Invalid task number for marking.");
                }
            } else if (input.startsWith("unmark ")) {
                try {
                    int index = Integer.parseInt(input.substring(7).trim()) - 1;
                    taskList.unmarkTask(index);
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + taskList.getTask(index));
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    System.out.println("Invalid task number for unmarking.");
                }
            } else if (input.isEmpty()) {
                System.out.println("No input, type something roe.");
            } else {
                // Default case: add a new Task
                taskList.addTask(input);
                System.out.println("added: " + input);
            }
        }
    }

    public static void main(String[] args) {
        new Caviar().run();
    }
}
