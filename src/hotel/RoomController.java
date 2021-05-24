package hotel;

import Models.Room;
import Models.User;
import com.mysql.cj.log.Log;
import home.DatabaseConnection;
import home.Main;
import home.RoomsManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BackgroundFill;
import login.LoginManager;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class RoomController {


    public static Room nextRoom(String type)
    {
        double price = 0.0;
        String label = "Cheap1";
        if (type.equals("CHEAP"))
        {
            price = 10.0;
        }
        else if (type.equals("DEF"))
        {
            price = 20.0;
            label = "Default1";
        }
        else
        {
            price = 30.0;
            label = "Deluxe1";
        }
        Room room = new Room();
        room.setCapacity(0);
        room.setPrice(price);
        room.setIdRoom(UUID.randomUUID().toString());
        room.setName(label);
        room.setType(type);
        return room;
    }

    public void updateDBRoom() throws Exception
    {
        for(Room room : RoomsManager.getInstance().getRooms()) {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            Statement statement = connectDB.createStatement();
            String insertQuery = "INSERT INTO `rooms`(`idRoom`,`price`,`capacity`,`name`,`type`)" +
                    "VALUES('" + room.getIdRoom() + "','" + room.getPrice() + "','" + room.getCapacity() + "'," +
                    "'" + room.getName() + "','" + room.getType() + "')";
            statement.executeUpdate(insertQuery);
        }
    }

    public void AddRoom()
    {
        RoomsManager.getInstance().addRoom(nextRoom("CHEAP"));
        RoomsManager.getInstance().addRoom(nextRoom("DEF"));
        RoomsManager.getInstance().addRoom(nextRoom("DELUXE"));
    }

    }
