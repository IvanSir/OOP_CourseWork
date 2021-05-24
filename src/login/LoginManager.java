package login;

import Models.User;

public class LoginManager {
    private static LoginManager instance = new LoginManager();

    public static LoginManager getInstance()
    {
        return instance;
    }

    private User user = new User();

    public void setUser(User user)
    {
        this.user = user;
    }

    public User getUser(){
        return user;
    }

}
