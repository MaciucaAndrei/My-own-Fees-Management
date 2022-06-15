package project.licenta;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import project.licenta.notifications.NotificationObservable;
import project.licenta.notifications.NotificationObserver;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;

public class MainApp extends Application {

    private CdiContainer cdiContainer;

    private final NotificationObservable notificationObservable = new NotificationObservable();

    private final NotificationObserver notificationObserver = new NotificationObserver();

    private boolean opened=true;

    public static void main(String[] args) {
        launch();
    }

    public Boolean isOpen()
    {
        return opened;
    }
    private void initCdiContainer() {
        cdiContainer = CdiContainerLoader.getCdiContainer();
        cdiContainer.boot();
        cdiContainer.getContextControl().startContexts();
    }

    @Override
    public void start(Stage stage) throws IOException, AWTException {
        initCdiContainer();
        File path = FileUtils.getUserDirectory().getAbsoluteFile();
        File file = new File(path.getAbsolutePath() + File.separator + "user.txt");
        if (file.createNewFile()) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 807, 469);
            stage.setTitle("myUniSit");
            stage.setScene(scene);
            stage.show();
        } else {

            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String[] validation = sc.nextLine().split(";");
                if (validation[1].equals("true")) {
                    notificationObservable.addObserver(notificationObserver);
                    notificationObservable.setUser(validation[0]);
                    notificationObservable.setToday(Calendar.getInstance());
                    Thread th = new Thread(notificationObservable);
                    th.setDaemon(true);
                    th.start();
                    FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("menu.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 861.6, 616);
                    stage.setTitle("myUniSit");
                    stage.setScene(scene);
                    Menu menu = fxmlLoader.getController();
                    menu.start(validation[0]);
                    stage.show();
                } else if (validation[1].equals("false")) {
                    FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 807, 469);
                    stage.setTitle("myUniSit");
                    stage.setScene(scene);
                    stage.show();
                }
            }
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        opened=false;
        cdiContainer.shutdown();
    }
}
