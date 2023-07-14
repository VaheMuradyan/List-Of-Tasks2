import java.util.Scanner;

public class ConsoleInterface {

        private static Operator operator;
        private static Scanner scanner;

        public static void main(String[] args) {
            operator = new Operator();
            scanner = new Scanner(System.in);

            displayMenu();
            int choice = getUserChoice();

            while (choice != 0) {
                switch (choice) {
                    case 1:
                        handleBooking();
                        break;
                    case 2:
                        handleAddRoom();
                        break;
                    case 3:
                        handleAddCustomer();
                        break;
                    case 4:
                        handleSaveState();
                        break;
                    case 5:
                        handleLoadState();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }

                displayMenu();
                choice = getUserChoice();
            }

            System.out.println("Thank you for using the booking system. Goodbye!");
        }

        private static void displayMenu() {
            System.out.println("\n===== Booking System Menu =====");
            System.out.println("1. Book a room");
            System.out.println("2. Add a room");
            System.out.println("3. Add a customer");
            System.out.println("4. Save state");
            System.out.println("5. Load state");
            System.out.println("0. Exit");
            System.out.println("===============================");
        }

        private static int getUserChoice() {
            System.out.print("Enter your choice: ");
            return scanner.nextInt();
        }

        private static void handleBooking() {
            System.out.print("Enter customer name: ");
            String name = scanner.next();
            System.out.print("Enter customer email: ");
            String email = scanner.next();
            System.out.print("Enter customer's available money: ");
            double money = scanner.nextDouble();

            Customer customer = operator.addCustomer(name, email, money);

            System.out.println("Room Types:");
            System.out.println("1. Single Room");
            System.out.println("2. Double Room");
            System.out.println("3. Deluxe Room");
            System.out.print("Enter room type number: ");
            int roomTypeChoice = scanner.nextInt();

            RoomType roomType = null;
            switch (roomTypeChoice) {
                case 1:
                    roomType = new RoomType.SingleRoom();
                    break;
                case 2:
                    roomType = new RoomType.DoubleRoom();
                    break;
                case 3:
                    roomType = new RoomType.DeluxeRoom();
                    break;
                default:
                    System.out.println("Invalid room type choice. Booking canceled.");
                    return;
            }

            Room room = operator.addRoom(roomType);

            System.out.print("Enter number of days: ");
            int days = scanner.nextInt();

            operator.booking(customer, room, days);
        }

        private static void handleAddRoom() {
            System.out.println("Room Types:");
            System.out.println("1. Single Room");
            System.out.println("2. Double Room");
            System.out.println("3. Deluxe Room");
            System.out.print("Enter room type number: ");
            int roomTypeChoice = scanner.nextInt();

            RoomType roomType = null;
            switch (roomTypeChoice) {
                case 1:
                    roomType = new RoomType.SingleRoom();
                    break;
                case 2:
                    roomType = new RoomType.DoubleRoom();
                    break;
                case 3:
                    roomType = new RoomType.DeluxeRoom();
                    break;
                default:
                    System.out.println("Invalid room type choice. Room addition canceled.");
                    return;
            }

            Room room = operator.addRoom(roomType);
            System.out.println("Room with ID " + room.getId() + " added successfully.");
        }

        private static void handleAddCustomer() {
            System.out.print("Enter customer name: ");
            String name = scanner.next();
            System.out.print("Enter customer email: ");
            String email = scanner.next();
            System.out.print("Enter customer's available money: ");
            double money = scanner.nextDouble();

            Customer customer = operator.addCustomer(name, email, money);
            System.out.println("Customer with name " + customer.getName() + " added successfully.");
        }

        private static void handleSaveState() {
            System.out.print("Enter file path to save state: ");
            String path = scanner.next();

            operator.saveState(path);
            System.out.println("State saved successfully.");
        }

        private static void handleLoadState() {
            System.out.print("Enter file path to load state from: ");
            String path = scanner.next();

            operator.getState(path);
            System.out.println("State loaded successfully.");
        }
}
