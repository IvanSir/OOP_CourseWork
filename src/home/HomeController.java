package home;

import javafx.fxml.FXML;
import login.LoginManager;
import javafx.scene.control.*;

import java.io.IOException;

public class HomeController {


    @FXML
    private Button buttonLog;

    @FXML
    private Button buttonReg;


    public void Signing() throws IOException {
        Main m = new Main();
        m.changeScene("../register/register.fxml");
    }

    public void Logging() throws IOException {
        Main m = new Main();
        m.changeScene("../login/login.fxml");
    }


}
