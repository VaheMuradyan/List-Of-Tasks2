import java.util.Objects;


public class Room {
    private int id;
    private RoomType roomType;

    public Room() {
    }

    public Room(RoomType roomType, int id) {
        this.roomType = roomType;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomType=" + roomType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
