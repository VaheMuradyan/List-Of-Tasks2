abstract class RoomType {
    private final double price;
    private final String info;

    public RoomType(double price, String info) {
        this.price = price;
        this.info = info;

    }

    public double getPrice() {
        return price;
    }

    public String getInfo() {
        return info;
    }

    public static class SingleRoom extends RoomType {
        private static final double price = 20;
        private static final String info = "Singl bad, bathroom, TV, closet";

        public SingleRoom() {
            super(price, info);
        }
    }

    public static class DoubleRoom extends RoomType {
        private static final double price = 20;
        private static final String info = "Double bad, bathroom, TV, closet";

        public DoubleRoom() {
            super(price, info);
        }
    }

    public static class DeluxeRoom extends RoomType {
        private static final double price = 55;
        private static final String info = "minibar, bathub, king-size-bad, sitting area";

        public DeluxeRoom() {
            super(price, info);
        }
    }

}




