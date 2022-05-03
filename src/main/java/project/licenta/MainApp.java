package project.licenta;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;

import java.io.IOException;

public class MainApp extends Application {

    private CdiContainer cdiContainer;

    public static void main(String[] args) {
        launch();
    }


    private void initCdiContainer() {
        cdiContainer = CdiContainerLoader.getCdiContainer();
        cdiContainer.boot();
        cdiContainer.getContextControl().startContexts();
    }
    @Override
    public void start(Stage stage) throws IOException {


        initCdiContainer();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 807, 469);
        stage.setTitle("myUniSit");
        stage.setScene(scene);
        stage.show();


    }


}
