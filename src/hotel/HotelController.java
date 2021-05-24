package hotel;

import home.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import login.LoginManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HotelController implements Initializable {

    @FXML
    private Label userName;

    public void goCheap() throws IOException
    {
        Main m = new Main();
        m.changeScene("../hotel/Cheap/cheap.fxml");

    }

    public void goDef() throws IOException
    {
        Main m = new Main();
        m.changeScene("../hotel/Def/def.fxml");
    }

    public void goDeluxe() throws IOException
    {
        Main m = new Main();
        m.changeScene("../hotel/Deluxe/deluxe.fxml");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userName.setText(LoginManager.getInstance().getUser().getName());
    }

    public void logOut()
    {
        try{
            LoginManager.getInstance().setUser(null);
            Main main = new Main();
            main.changeScene("../home/home.fxml");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
