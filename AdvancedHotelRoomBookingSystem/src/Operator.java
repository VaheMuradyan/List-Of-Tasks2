import java.io.*;
import java.util.LinkedList;
import java.util.List;

class Operator {
    public static List<Room> roomList = new LinkedList<>();
    public static List<Customer> customersList = new LinkedList<>();


    //chimaca vonc erkar jamanakov pahem arjeq@ isAvalbale = false;
    //ete heto aseq Threadov petqa anei meka chem havatalu;
    //ete aselueq cuyc tveq orinakov
    public static void booking(Customer customer, Room room, int days) {
        if (room.isAvailable()) {
            double allPrice = billGeneretion(room,days);
            room.addCustomerToHystory(customer);
            customer.addRoomToHystory(room);
            customer.many -= allPrice;
            System.out.println("Room price is " + allPrice);
            room.setAvailable(true);
        } else {
            System.out.println("No such room is avelable");
        }

    }

    private static double billGeneretion(Room room, int days){
        double texes = room.getRoomType().getPrice() * 20 / 100;
        texes *= days;
        double serviceFees = room.getRoomType().getPrice() * 10 / 100;
        double detailedBill = room.getRoomType().getPrice() + texes + serviceFees;
        return detailedBill;
    }

    public static Room addRoom(RoomType e){
        roomList.add(new Room(e));
        return roomList.get(0);
    }

    public static Customer  addCustomer(String name, String mail, double many){
        customersList.add(new Customer(name,mail,many));
        return customersList.get(0);
    }

    public void saveState(String path){
        File file = new File(path);
        try {
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for(int i = 0; i < customersList.size(); i++){
                bufferedWriter.write(customersList.get(i).toString());
            }
            bufferedWriter.flush();
            for(int i = 0; i < roomList.size(); i++){
                bufferedWriter.write(roomList.get(i).toString());
            }
            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void getState(String path){
        File file = new File(path);
        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            while((s = bufferedReader.readLine()) != null){
                //customersList.add();
                //chgitem irakanacum@
            }
        }catch (FileNotFoundException e){
            System.out.println("Non");
        }catch (IOException e){
            System.out.println("Non");
        }

    }
}
