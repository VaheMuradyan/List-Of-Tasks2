import java.util.List;
import java.util.LinkedList;
class Room {
    private final int id = this.hashCode();
    private final RoomType roomType;
    private boolean isAvailable;
    private List<Customer> customersHystory = new LinkedList<>();

    public Room(RoomType roomType) {
        this.roomType = roomType;
        this.isAvailable = true;
    }

    public int getId() {
        return id;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public List<Customer> getCustomersHystory() {
        return customersHystory;
    }

    public void setAvailable(boolean isAvailable){
        this.isAvailable = isAvailable;
    }

    public void showInfo(){
        System.out.println(roomType.getInfo());
    }

    public void addCustomerToHystory(Customer customer){
        customersHystory.add(customer);
        isAvailable = false;
    }
}


