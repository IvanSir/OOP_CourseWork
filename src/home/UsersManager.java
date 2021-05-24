package home;

import Models.User;
import java.util.ArrayList;

public class UsersManager {
    private static UsersManager instance = new UsersManager();

    public static UsersManager getInstance()
    {
        return instance;
    }

    private ArrayList<User> users = new ArrayList<User>();

    public void addUser(User user)
    {
        users.add(user);
    }

    public void removeUser(String userName)
    {
        users.removeIf(user -> user.getUserName().equals(userName));
    }


    public ArrayList<User> getUsers(){
        return users;
    }
}
