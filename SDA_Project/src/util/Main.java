package util;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

//import java.sql.*;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Testing the database connection
//            Statement statement = DBConnection.getConnection();
            
//            DBConnection.Dispose();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/managerViews/mainPage.fxml"));
            AnchorPane root = loader.load();

            primaryStage.setTitle("Airline Management System");
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(true);
//    		primaryStage.setFullScreen(true);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
