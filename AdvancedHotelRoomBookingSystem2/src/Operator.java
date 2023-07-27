import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class Operator {
    private static final String ROOM_PATH = "src/files/room.txt";
    private static final String CUSTOMER_PATH = "src/files/customer.txt";
    private static final String BOOKING_HISTORY_PATH = "src/files/bookingHistory.txt";

    private static final String BILLING_PATH = "src/files/billing/";
    private static final String REPORT_PATH = "src/files/reports/";

    public static void creatNewRoom(Scanner scanner) {
        displayPrompt("===== Add new room ====");
        List<Room> rooms = readRooms();
        Room room;
        System.out.println("Single Room (1)");
        System.out.println("Double Room (2)");
        System.out.println("Deluxe Room (3)");
        int userInput = getUserInput(scanner, 1, 3, "Enter room type: ");
        RoomType roomType = RoomType.getType(userInput);
        int roomId = getUserInput(scanner, 1, Integer.MAX_VALUE, "Enter room id: ");
        if (rooms.stream().map(Room::getId).noneMatch(integer -> integer == roomId)) {
            room = new Room(roomType, roomId);
            rooms.add(room);
            writeRooms(rooms);
        } else {
            System.out.println("RoomId should be unique: " + roomId);
        }
    }

    private static List<Room> readRooms() {
        List<Room> rooms = new ArrayList<>();
        try {
            Path path = Path.of(ROOM_PATH);
            if (Files.exists(path)) {
                byte[] jsonData = Files.readAllBytes(path);
                String jsonString = new String(jsonData);
                ObjectMapper objectMapper = new ObjectMapper();
                rooms = objectMapper.readValue(jsonString, objectMapper.getTypeFactory().constructCollectionType(List.class, Room.class));
            } else {
                return rooms;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return rooms;
    }

    private static void writeRooms(List<Room> rooms) {
        ObjectMapper objectMapper = new ObjectMapper();
        Path filePath = Paths.get(ROOM_PATH);
        String jsonString;
        try {
            jsonString = objectMapper.writeValueAsString(rooms);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        try {
            Files.write(filePath, jsonString.getBytes());
            System.out.println("Successfully written to file: " + filePath.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error writting json to file: " + e.getMessage());
        }
    }

    public static void creatNewCustomer(Scanner scanner) {
        displayPrompt("===== Add new customer =====");
        List<Customer> customers = readCustomers();
        while (true) {
            String name = getUserInput(scanner, "Enter customer name: ");
            String mail = getUserInput(scanner, "Enter customer mail: ");
            if (customers.stream().map(Customer::getMail).noneMatch(email -> email.equals(mail))) {
                try {
                    Customer customer = new Customer(name, mail);
                    customers.add(customer);
                    writeCustomers(customers);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Mail should be unique " + mail);
            }
        }
    }

    private static List<Customer> readCustomers() {
        List<Customer> customers = new ArrayList<>();
        try {
            Path path = Path.of(CUSTOMER_PATH);
            if (Files.exists(path)) {
                byte[] jsonData = Files.readAllBytes(path);
                String jsonString = new String(jsonData);
                ObjectMapper objectMapper = new ObjectMapper();
                customers = objectMapper.readValue(jsonString, objectMapper.getTypeFactory().constructCollectionType(List.class, Customer.class));
            } else {
                return customers;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return customers;
    }

    private static void writeCustomers(List<Customer> customers) {
        ObjectMapper objectMapper = new ObjectMapper();
        Path filePath = Paths.get(CUSTOMER_PATH);
        String jsonString;
        try {
            jsonString = objectMapper.writeValueAsString(customers);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        try {
            Files.write(filePath, jsonString.getBytes());
            System.out.println("Successfully written to file: " + filePath.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error writting json to file: " + e.getMessage());
        }
    }


    public static void roomBooking(Scanner scanner) {
        displayPrompt("===== Booking a room ======");
        if (readRooms().isEmpty()) {
            System.out.println("There is no Room in the system. Please add new Room");
            return;
        }
        List<RoomBooking> roomBookings = readBookingHistory();
        LocalDate checkInDate;
        LocalDate checkOutDate;
        List<Room> availableRooms;
        Room chosenRoom;
        Customer customer;
        while (true) {
            displayPrompt("Enter checkin date");
            int inDay = getUserInput(scanner, 1, 31, "Enter day [1 ... 31]: ");
            int inMonth = getUserInput(scanner, 1, 12, "Enter month [1 ... 12]: ");
            int inYear = getUserInput(scanner, LocalDate.now().getYear(), 2030, "Enter year [" + LocalDate.now().getYear() + " ... 2030]: ");

            displayPrompt("Enter checkout date");
            int outDay = getUserInput(scanner, 1, 31, "Enter day [1 ... 31]: ");
            int outMonth = getUserInput(scanner, 1, 12, "Enter month [1 ... 12]: ");
            int outYear = getUserInput(scanner, LocalDate.now().getYear(), 2030, "Enter year [" + LocalDate.now().getYear() + " ... 2030]: ");

            checkInDate = LocalDate.of(inYear, inMonth, inDay);
            checkOutDate = LocalDate.of(outYear, outMonth, outDay);
            int num = checkOutDate.compareTo(checkInDate);

            if (num <= 0) {
                System.out.println("Checkout date should be greater then checkIn date");
            } else {
                availableRooms = getAvailableRooms(checkInDate);
                if (availableRooms.isEmpty()) {
                    System.out.println("There are no available rooms. Please choose other dates");
                } else {
                    break;
                }
            }
        }

        while (true) {
            System.out.println("Choose rooms from suggested list:");
            for (Room room : availableRooms) {
                System.out.println(room);
            }
            int roomId = getInput(scanner, "Enter Room id: ");
            List<Room> rooms = availableRooms.stream().filter(room -> room.getId() == roomId).toList();
            if (rooms.isEmpty()) {
                System.out.println("You choose incorrect room Id");
            } else {
                chosenRoom = rooms.get(0);
                break;
            }
        }

        while (true) {
            System.out.println("Enter customer data");
            String name = getUserInput(scanner, "Enter Customer name: ");
            String mail = getUserInput(scanner, "Enter Customer mail: ");

            List<Customer> customers = readCustomers();
            try {
                customer = new Customer(name, mail);
                boolean isNewCustomer = customers.stream().noneMatch(customer1 -> customer1.getMail().equals(mail));
                if (isNewCustomer) {
                    customers.add(customer);
                    writeCustomers(customers);
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        RoomBooking currentBooking = new RoomBooking(customer, chosenRoom, checkInDate, checkOutDate);
        roomBookings.add(currentBooking);

        writeBookingHistory(roomBookings);
        System.out.println("Your booking successfully done ");
        System.out.println("===== Your bill =====");
        DetailedBill detailedBill = new DetailedBill(currentBooking);
        detailedBill.showBill();
        String choose = getUserInput(scanner, "Y", "N", "Do you want to save bill in the file? [Y/N]: ");
        switch (choose) {
            case "N":
                break;
            case "Y":
                saveDetailedBill(detailedBill);
                break;
        }
    }

    private static List<Room> getAvailableRooms(LocalDate dateToCheck) {
        List<RoomBooking> roomBookings = readBookingHistory();
        List<Room> availableRooms = readRooms();
        List<Room> busyRooms = roomBookings.stream()
                .filter(roomBooking -> checkDateInRange(roomBooking.getCheckInDate(), roomBooking.getCheckOutDate(), dateToCheck))
                .map(RoomBooking::getRoom).toList();
        availableRooms.removeAll(busyRooms);
        return availableRooms;
    }

    private static boolean checkDateInRange(LocalDate startDate, LocalDate endDate, LocalDate dateToCheck) {
        return !dateToCheck.isBefore(startDate) && !dateToCheck.isAfter(endDate);
    }

    private static List<RoomBooking> readBookingHistory() {
        List<RoomBooking> roomBookings = new ArrayList<>();
        try {
            Path path = Path.of(BOOKING_HISTORY_PATH);
            if (Files.exists(path)) {
                byte[] jsonData = Files.readAllBytes(path);
                String jsonString = new String(jsonData);
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                roomBookings = objectMapper.readValue(jsonString, objectMapper.getTypeFactory().constructCollectionType(List.class, RoomBooking.class));
            } else {
                return roomBookings;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return roomBookings;
    }

    private static void writeBookingHistory(List<RoomBooking> roomBookings) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Path filePath = Paths.get(BOOKING_HISTORY_PATH);
        String jsonString;
        try {
            jsonString = objectMapper.writeValueAsString(roomBookings);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        try {
            Files.write(filePath, jsonString.getBytes());
            System.out.println("Successfully written to file: " + filePath.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error writting json to file: " + e.getMessage());
        }
    }

    private static void saveDetailedBill(DetailedBill detailedBills) {
        String filename = Instant.now().toEpochMilli() + ".txt";
        Path filePath = Paths.get(BILLING_PATH + filename);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonString;
        try {
            jsonString = objectMapper.writeValueAsString(detailedBills);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        try {
            Files.write(filePath, jsonString.getBytes());
            System.out.println("Successfully written to file: " + filePath.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error writting json to file: " + e.getMessage());
        }
    }


    private static void displayPrompt(String prompt) {
        System.out.println(prompt);
    }


    private static int getUserInput(Scanner scanner, int min, int max, String prompt) {
        int choice;
        while (true) {
            System.out.print(prompt);
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

    private static String getUserInput(Scanner scanner, String opt1, String opt2, String prompt) {
        String choice;
        while (true) {
            System.out.print(prompt);
            choice = scanner.nextLine();
            if (choice.toUpperCase().equals(opt1) || choice.toUpperCase().equals(opt2)) {
                return choice.toUpperCase();
            } else {
                scanner.nextLine();
            }
            System.out.println("Invalid input. Please enter a " + opt1 + " or " + opt2);
        }
    }

    private static String getUserInput(Scanner scanner, String prompt) {
        String choice;
        System.out.print(prompt);
        choice = scanner.nextLine();
        return choice;
    }

    private static int getInput(Scanner scanner, String prompt) {
        int choice;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                return choice;
            } else {
                scanner.nextLine();
            }
            System.out.println("Invalid input.");
        }
    }


    public static void makeReport(Scanner in) {
        List<Room> roomList = readRooms();
        if (roomList.isEmpty()) {
            System.out.println("There is no Room in the system. Please add new Room");
            return;
        }
        System.out.println("===== Report types =====");
        System.out.println("Report for a specific room [1]");
        System.out.println("Report upcoming bookings [2]");
        int num = getUserInput(in, 1, 2, "Enter report type: ");
        switch (num) {
            case 1 -> specificRoomReport(in);
            case 2 -> upcomingBookingReport();
        }
    }

    private static void upcomingBookingReport() {
        List<RoomBooking> roomBookingList = readBookingHistory();
        List<RoomBooking> upcomingBookings = roomBookingList.stream().filter(roomBooking -> roomBooking.getCheckInDate().isAfter(LocalDate.now())).toList();
        saveReports(upcomingBookings);
    }

    private static void specificRoomReport(Scanner in) {
        while (true) {
            int roomId = getInput(in, "Enter room id: ");
            List<Room> roomList = readRooms();
            boolean isRoomExist = roomList.stream().anyMatch(room -> room.getId() == roomId);
            if (isRoomExist) {
                List<RoomBooking> roomBookings = readBookingHistory();
                List<RoomBooking> roomBookingList = roomBookings.stream().filter(roomBooking -> roomBooking.getRoom().getId() == roomId).toList();
                saveReports(roomBookingList);
                break;
            } else {
                System.out.println("Invalid Room Id");
            }
        }
    }

    private static void saveReports(List<RoomBooking> roomBookings) {
        String filename = roomBookings.get(0).getRoom().getId() + "_" + Instant.now().toEpochMilli() + ".txt";
        Path filePath = Paths.get(REPORT_PATH + filename);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonString;
        try {
            jsonString = objectMapper.writeValueAsString(roomBookings);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        try {
            Files.write(filePath, jsonString.getBytes());
            System.out.println("Successfully written to file: " + filePath.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error writing json to file: " + e.getMessage());
        }
    }
}
