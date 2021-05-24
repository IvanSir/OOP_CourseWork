package home;

import Models.Room;
import Models.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import login.LoginManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class Main extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        usersInitManager();
       // roomsInitManager();
        primaryStage.setTitle("GG");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public void changeScene(String fxml) throws IOException{
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stage.getScene().setRoot(pane);
    }

    public void usersInitManager() throws Exception
    {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String allQuery = "SELECT * FROM users";
        PreparedStatement preparedStatement = connectDB.prepareStatement(allQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        do {
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
            UsersManager.getInstance().addUser(user);
            resultSet.next();
        } while (!resultSet.isAfterLast());


    }

    public void roomsInitManager() throws Exception
    {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String allQuery = "SELECT * FROM rooms";
        PreparedStatement preparedStatement = connectDB.prepareStatement(allQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        do {
            Room room = new Room();
            room.setIdRoom(resultSet.getString(1));
            room.setName(resultSet.getString(6));
            room.setPrice(parseDouble(resultSet.getString(2)));
            room.setCapacity(parseInt(resultSet.getString(5)));
            room.setType(resultSet.getString(7));
            room.setCheckIn(resultSet.getDate(3));
            room.setCheckOut(resultSet.getDate(4));
            RoomsManager.getInstance().addRoom(room);
            resultSet.next();
        } while (!resultSet.isAfterLast());


    }

    public static void main(String[] args) {
        launch(args);
    }
}
