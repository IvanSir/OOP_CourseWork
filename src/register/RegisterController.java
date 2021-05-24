package register;

import Models.User;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import home.DatabaseConnection;
import home.Main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.UUID;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class RegisterController {

    public TextField name = new TextField("name");
    public TextField surName = new TextField("surName");
    public TextField userName = new TextField("userName");
    public TextField number = new TextField("number");
    public TextField age = new TextField("age");
    public TextField money = new TextField("money");
    public TextField password = new TextField("password");
    public Label invalidData;

    public void Signed() throws IOException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        User user = new User();
        try {
            user.setIdUser(UUID.randomUUID().toString());
            user.setName(name.getText());
            user.setSurName(surName.getText());
            user.setUserName(userName.getText());
            user.setNumber(number.getText());
            user.setAge(parseInt(age.getText()));
            user.setMoney(parseDouble(money.getText()));
            user.setPassword(password.getText());
            if (user.getName().isEmpty() || user.getUserName().isEmpty() || user.getPassword().isEmpty())
                throw new IllegalArgumentException();
        }
        catch(Exception e)
        {
            invalidData.setText("Invalid data");
            return;
        }
        try {
            Statement statement = connectDB.createStatement();
            String insertQuery = "INSERT INTO `users`(`idUser`,`name`,`surName`,`userName`,`number`,`money`,`age`,`password`) " +
                                    "VALUES('"+user.getIdUser()+"','"+user.getName()+"','"+user.getSurName()+"'," +
                                            "'"+user.getUserName()+"','"+user.getNumber()+"','"+user.getMoney()+"'," +
                                                "'"+user.getAge()+"','"+user.getPassword()+"')";
            statement.executeUpdate(insertQuery);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        Main m = new Main();
        m.changeScene("../login/login.fxml");
    }

    public void goBack() throws IOException
    {
        Main m = new Main();
        m.changeScene("../home/home.fxml");
    }
}
