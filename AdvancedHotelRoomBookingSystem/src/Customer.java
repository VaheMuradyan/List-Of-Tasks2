import java.util.List;
import java.util.LinkedList;
class Customer {
    public double many;
    private final String name;
    private final String email;
    private List<Room> bookingRoomsHystory = new LinkedList<>();

    public Customer(String name, String email, double many) {
        this.name = name;
        this.email = email;
        this.many = many;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void addRoomToHystory(Room room){
        bookingRoomsHystory.add(room);
    }

    public List<Room> getBookingRoomsHystory() {
        return bookingRoomsHystory;
    }
}
