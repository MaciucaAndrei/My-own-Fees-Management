package project.licenta;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.licenta.dbThread.DbThread;

import java.io.IOException;
import java.sql.SQLException;

public class MainApp extends Application {


    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
        DbThread trhead = new DbThread();
        new Thread(trhead).start();
    }

    public static void main(String[] args) {
        launch();
    }


}
