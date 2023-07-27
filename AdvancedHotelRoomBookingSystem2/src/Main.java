import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        Scanner in = new Scanner(System.in);
        displayPrompt();
        while (true) {
            displayMainMenu();
            int choice = getUserInput(in, 1, 4);
            switch (choice) {
                case 1:
                    Operator.creatNewRoom(in);
                    break;
                case 2:
                    Operator.creatNewCustomer(in);
                    break;
                case 3:
                    Operator.roomBooking(in);
                    break;
                case 4:
                    Operator.makeReport(in);

            }
        }
    }

    private static void displayPrompt() {
        System.out.println("====== WELCOME HOTEL ROOM BOOKING SYSTEM");
    }

    private static void displayMainMenu() {
        System.out.println("Add room [1]");
        System.out.println("Add customer [2]");
        System.out.println("Booking room [3]");
        System.out.println("Make a report [4]");
    }

    private static int getUserInput(Scanner scanner, int min, int max) {
        int choice;
        while (true) {
            System.out.print("Enter your choice: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice >= min && choice <= max) {
                    return choice;
                }
            } else {
                scanner.nextLine();
            }
            System.out.println("Invalid input. Please enter a valid number between " + min + " and " + max);
        }
    }
}