import java.time.LocalDate;

public class RoomBooking {
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Customer customer;
    private Room room;

    public RoomBooking(Customer customer, Room room, LocalDate checkIn, LocalDate checkOut) {
        this.customer = customer;
        this.room = room;
        checkInDate = checkIn;
        checkOutDate = checkOut;
    }

    RoomBooking() {

    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "RoomBooking{" +
                "checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", customer=" + customer +
                ", room=" + room +
                '}';
    }
}
