package login;
import Models.User;
import hotel.HotelController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import home.DatabaseConnection;
import home.Main;
import javafx.scene.layout.Region;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class LoginController {

    @FXML
    private Button buttonLog;

    @FXML
    private Label wrongLogin;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;


    public void Logged() throws IOException{
        LoginManager.getInstance().setUser(authorize());
    }

    private User authorize() throws IOException{

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        try {
            String insertQuery = "SELECT * FROM motel.users WHERE userName = ? ";
            PreparedStatement preparedStatement = connectDB.prepareStatement(insertQuery);
            preparedStatement.setString(1,username.getText());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if(resultSet.getString(8).equals(password.getText())){
                User user = new User();
                user.setIdUser(resultSet.getString(1));
                user.setName(resultSet.getString(2));
                user.setSurName(resultSet.getString(3));
                user.setUserName(resultSet.getString(4));
                user.setAge(parseInt(resultSet.getString(5)));
                user.setNumber(resultSet.getString(6));
                user.setMoney(parseDouble(resultSet.getString(7)));
                user.setPassword(resultSet.getString(8));
                user.setIdRoom(resultSet.getString(9));
                LoginManager.getInstance().setUser(user);

                Main m = new Main();
                m.changeScene("../hotel/hotel.fxml");
                return user;
            }
            else
            {
                wrongLogin.setText("Invalid login or password");
            }
        }
        catch(Exception e)
        {
            wrongLogin.setText("Invalid login or password");
        }
        return null;
    }

    public void goBack() throws IOException
    {
        Main m = new Main();
        m.changeScene("../home/home.fxml");
    }
}
