package home;

import Models.Room;
import Models.User;
import java.util.ArrayList;

public class RoomsManager {
    private static RoomsManager instance = new RoomsManager();

    public static RoomsManager getInstance()
    {
        return instance;
    }

    private ArrayList<Room> rooms = new ArrayList<Room>();

    public void addRoom(Room room)
    {
        rooms.add(room);
    }

    public void removeRoom(String userName)
    {
        rooms.removeIf(user -> user.getName().equals(userName));
    }


    public ArrayList<Room> getRooms(){
        return rooms;
    }
}
