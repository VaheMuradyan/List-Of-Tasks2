enum RoomType {
    SingleRoom(20, "Single room, Bathroom, TV, Closet"),
    DoubleRoom(35, "Double room, Bathroom, TV, Closet"),
    DeluxeRoom(55, "Minibar, Bathroom, King-Size bed, Sitting area");
    private final double price;
    private final String info;

    RoomType(double price, String info) {
        this.price = price;
        this.info = info;
    }

    public double getPrice() {
        return price;
    }

    public String getInfo() {
        return info;
    }

    public static RoomType getType(int id) {
        return switch (id) {
            case 1 -> SingleRoom;
            case 2 -> DoubleRoom;
            case 3 -> DeluxeRoom;
            default -> throw new IllegalArgumentException("Invalid room type");
        };
    }

}
