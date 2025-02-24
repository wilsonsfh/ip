package caviar.ui;

import java.util.Scanner;

/**
 * Handles user interaction with Caviar.
 */
public class Ui {
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message.
     */
    public void showWelcome() {
        System.out.println("Hello! I'm Caviar. Roe!");
        System.out.println("What can I do for you?");
        System.out.println("______________________\n");
    }

    /**
     * Reads user input.
     * @return User command as a String.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Displays the given message.
     * @param message Message to display.
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays the divider line.
     */
    public void showLine() {
        System.out.println("______________________");
    }

    /**
     * Closes the scanner.
     */
    public void close() {
        scanner.close();
    }
}
