import java.util.ArrayList;
import java.util.Scanner;

public class Caviar {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ArrayList<String> tasks = new ArrayList<>();
        System.out.println("Hello! I'm Caviar");
        System.out.println("What can I do for you?");
        System.out.println("______________________");

        boolean toggle = true;
        while (toggle) {
            String inData = scan.nextLine().trim();
            if (inData.equals("bye")) {
                System.out.println("Roe. Hope to see you again soon!");
                toggle = false;
            } else if (inData.equals("list")) {
                System.out.println("list");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i));
                }
            } else if (inData.isEmpty()) {
                System.out.println("No input, type something roe.");
            } else {
                tasks.add(inData);
                System.out.println("added: " + inData);
            }
        }
    }
}
