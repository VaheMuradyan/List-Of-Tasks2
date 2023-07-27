import java.time.LocalDate;
import java.io.Serializable;
import java.time.temporal.ChronoUnit;

class DetailedBill {
    RoomBooking roomBooking;
    private long bookingPeriod;
    private final double taxes = 20;
    private final double serviceFees = 10;
    private double totalAmount;

    DetailedBill() {
    }

    public DetailedBill(RoomBooking roomBooking) {
        this.roomBooking = roomBooking;
        this.bookingPeriod = ChronoUnit.DAYS.between(roomBooking.getCheckInDate(), roomBooking.getCheckOutDate());
        this.totalAmount = roomBooking.getRoom().getRoomType().getPrice() * bookingPeriod * (1 + taxes / 100) * (1 + serviceFees / 100);
    }

    public void showBill() {
        System.out.println("Room Id: " + roomBooking.getRoom().getId());
        System.out.println("Room type: " + roomBooking.getRoom().getRoomType());
        System.out.println("Customer name: " + roomBooking.getCustomer().getName());
        System.out.println("Customer mail: " + roomBooking.getCustomer().getMail());
        System.out.println("Period of booking: " + bookingPeriod);
        System.out.println("Price for booking for period: " + getTotalWithoutTaxes());
        System.out.println("Taxes (" + taxes + "%): " + getAmountWithTaxes());
        System.out.println("Service fee (" + serviceFees + "%): " + getAmountWithTaxesAndServiceFee());
        System.out.println("TOTAL AMOUNT: -> " + totalAmount);
    }

    private double getTotalWithoutTaxes() {
        return roomBooking.getRoom().getRoomType().getPrice() * bookingPeriod;
    }

    private double getAmountWithTaxes() {
        return getTotalWithoutTaxes() * taxes / 100;
    }

    private double getAmountWithTaxesAndServiceFee() {
        return (getAmountWithTaxes() + getTotalWithoutTaxes()) * serviceFees / 100;
    }

    public RoomBooking getRoomBooking() {
        return roomBooking;
    }

    public long getBookingPeriod() {
        return bookingPeriod;
    }

    public double getTaxes() {
        return taxes;
    }

    public double getServiceFees() {
        return serviceFees;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "DetailedBill {" +
                "roomBooking=" + roomBooking +
                ", bookingPeriod=" + bookingPeriod +
                ", taxes=" + taxes +
                ", serviceFees=" + serviceFees +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
