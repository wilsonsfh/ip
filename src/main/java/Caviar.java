import java.util.Scanner;

public class Caviar {
    public static void main(String[] args) {
        String inData = "";
        Boolean toggle = true;
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello! I'm Caviar\n" +
                "What can I do for you?\n" +
                "______________________\n");
        Scanner scan = new Scanner(System.in);
        while(toggle) {
            inData = scan.nextLine();
            if (!inData.equals("bye")) {

                if (inData.equals("")) {
                    System.out.println("no input, type something roe.");
                } else {
                    System.out.println(inData + " roe.");
                }
            }
            else {
                toggle = false;
                System.out.println("Roe. Hope to see you again soon!");
            }


        }
    }
}