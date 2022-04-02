package project.licenta;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.licenta.cdi.CDIContainerThread;

import java.io.IOException;

public class MainApp extends Application {


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();

        CDIContainerThread cdiContainerThread = new CDIContainerThread();
        Thread thread = new Thread(cdiContainerThread);
        thread.setDaemon(true);
        thread.start();
    }


}
