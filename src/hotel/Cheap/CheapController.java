package hotel.Cheap;

import Models.Room;
import Models.User;
import home.DatabaseConnection;
import home.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import login.LoginManager;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class CheapController implements Initializable {

    @FXML
    private Label userName;

    @FXML
    private Label guestsIn;

    @FXML
    private Label Cheap1;

    @FXML
    private Button buttonCheck1;

    @FXML
    private Label dateIn;

    @FXML
    private Label dateOut;

    @FXML
    private Label userMoney;

    public void goBack() throws IOException
    {
        Main main = new Main();
        main.changeScene("../hotel/hotel.fxml");
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

    public void AddRoom() throws IOException
    {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        Room room = new Room();
        room.setCapacity(0);
        room.setPrice(20.0);
        room.setIdRoom(UUID.randomUUID().toString());
        room.setName(Cheap1.getText());
        room.setType("CHEAP");

        try {
            Statement statement = connectDB.createStatement();
            String insertQuery = "INSERT INTO `rooms`(`idRoom`,`price`,`capacity`,`name`,`type`)" +
                    "VALUES('"+room.getIdRoom()+"','"+room.getPrice()+"','"+room.getCapacity()+"', '"+room.getName()+"','"+room.getType()+"')";
            statement.executeUpdate(insertQuery);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void checkInRoom() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        try {
            String roomQuery = "SELECT * FROM motel.rooms WHERE name = ?";
            PreparedStatement preparedRoom = connectDB.prepareStatement(roomQuery);
            preparedRoom.setString(1, Cheap1.getText());
            ResultSet resultRoom = preparedRoom.executeQuery();
            resultRoom.next();

            User user = LoginManager.getInstance().getUser();
            if (buttonCheck1.getText().equals("Check in") && user.getMoney() > 0) {
                if (user.getIdRoom() == null && parseInt(resultRoom.getString(5)) < 3) {
                    user.setIdRoom(resultRoom.getString(1));
                    Statement prepstatement = connectDB.createStatement();
                    String insertQuery = "UPDATE `users` SET idRoom = '" + resultRoom.getString(1) + "' WHERE userName = '" +user.getUserName() + "'";
                    PreparedStatement preparedStatement = connectDB.prepareStatement(insertQuery);
                    prepstatement.executeUpdate(insertQuery);

                    int users = parseInt(resultRoom.getString(5));
                    if (users == 0)
                    {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                        dateIn.setText(formatter.format(Calendar.getInstance().getTime()));
                        dateOut.setText("");
                        Statement dateState = connectDB.createStatement();
                        String datka = formatter.format(Calendar.getInstance().getTime());
                        String dateInQuery = "UPDATE `rooms` SET checkIn  = '"+datka+"', checkOut = '"+datka+"' WHERE name='"+Cheap1.getText()+"'";
                        PreparedStatement prepInDate = connectDB.prepareStatement(dateInQuery);
                        prepInDate.executeUpdate();
                    }
                    users++;
                    Statement roomstatement = connectDB.createStatement();
                    String updateRoom = "UPDATE `rooms` SET capacity = '" + users + "' WHERE name = '" + resultRoom.getString(6) + "'";
                    roomstatement.executeUpdate(updateRoom);
                    guestsIn.setText(users + "/3");
                    buttonCheck1.setText("Check Out");

                }
            }
            else if ( user.getIdRoom() !=  null && user.getIdRoom().equals(resultRoom.getString(1)))
            {
                String querySelect = "SELECT * FROM rooms WHERE name='"+Cheap1.getText()+"'";
                PreparedStatement preparedCheck = connectDB.prepareStatement(querySelect);
                ResultSet resultCheck = preparedCheck.executeQuery();
                resultCheck.next();

                java.util.Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(resultCheck.getString(3));
                java.util.Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(resultCheck.getString(4));


                long milliseconds = date2.getTime() - date1.getTime();
                int days = (int) (milliseconds / (24 * 60 * 60 * 1000));

                Statement outMent = connectDB.createStatement();

                String userQuery = "SELECT * FROM users WHERE idRoom = ?";
                PreparedStatement preparedUser = connectDB.prepareStatement(userQuery);
                preparedUser.setString(1, user.getIdRoom());
                ResultSet resultUser = preparedUser.executeQuery();
                resultUser.next();
                double money = parseDouble(resultUser.getString(7));
                money -= (days * parseDouble(resultRoom.getString(2)));

                user.setIdRoom(null);

                String updateQuery = "UPDATE `users` SET idRoom = null, money = '"+money+"' WHERE userName = '"+user.getUserName()+"'";
                outMent.executeUpdate(updateQuery);
                int users = parseInt(resultRoom.getString(5));
                users--;
                if (users == 0)
                {
                    dateOut.setText(resultRoom.getString(4));
                }
                Statement roomstatement = connectDB.createStatement();
                String updateRoom = "UPDATE `rooms` SET capacity = '" + users + "' WHERE name = '" + resultRoom.getString(6) + "'";
                roomstatement.executeUpdate(updateRoom);
                guestsIn.setText(users + "/3");
                buttonCheck1.setText("Check in");
                user.setMoney(money);
                userMoney.setText(String.valueOf(user.getMoney()));

            }


        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        User user = LoginManager.getInstance().getUser();
        try {
            String allRoomQuery = "SELECT * FROM motel.rooms WHERE name='"+Cheap1.getText()+"'";
            PreparedStatement preparedRoom = connectDB.prepareStatement(allRoomQuery);
            ResultSet resRoom = preparedRoom.executeQuery();
            resRoom.next();

            guestsIn.setText(resRoom.getString(5)+"/3");
            if (parseInt(resRoom.getString(5)) != 0)
                dateIn.setText(resRoom.getString(3));

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        if (user.getIdRoom() != null) {
            try {
                Statement statement = connectDB.createStatement();
                String roomQuery = "SELECT * FROM motel.rooms WHERE idRoom = '" + user.getIdRoom() + "'";
                PreparedStatement preparedRoom = connectDB.prepareStatement(roomQuery);
                ResultSet resultRoom = preparedRoom.executeQuery();
                resultRoom.next();
                if (resultRoom.getString(6).equals(Cheap1.getText()))
                buttonCheck1.setText("Check Out");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        userName.setText(user.getName());
        userMoney.setText(String.valueOf(user.getMoney()));

    }

    public void AddDay()
    {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        try {
            Statement statement = connectDB.createStatement();
            String nameH = Cheap1.getText();
            String roomQuery = "SELECT * FROM motel.rooms WHERE name = ?";
            PreparedStatement preparedRoom = connectDB.prepareStatement(roomQuery);
            preparedRoom.setString(1, Cheap1.getText());
            ResultSet resultRoom = preparedRoom.executeQuery();
            resultRoom.next();


            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            java.util.Date addedDate = new SimpleDateFormat("yyyy-MM-dd").parse(resultRoom.getString(4));
            Calendar c = Calendar.getInstance();
            c.setTime(addedDate);
            c.add(Calendar.DATE, 1);

            Statement dateState = connectDB.createStatement();
            String datka = formatter.format(c.getTime());
            String dateInQuery = "UPDATE `rooms` SET checkOut = '"+datka+"' WHERE name='"+Cheap1.getText()+"'";
            PreparedStatement prepInDate = connectDB.prepareStatement(dateInQuery);
            prepInDate.executeUpdate();


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}
