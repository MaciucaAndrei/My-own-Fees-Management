package project.licenta;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;

public class Costs {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnLibrary;

    @FXML
    private Button btnLogout;

    @FXML
    private Label lblMenu;

    @FXML
    private Label lblMenuClose;

    @FXML
    private AnchorPane paneMenu;

    @FXML
    private AnchorPane paneSlider;

    private String user;


    public void btnBackOnClick(ActionEvent event) throws IOException, AWTException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("menu.fxml"));
        Stage stage =(Stage) btnBack.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        Menu menu = loader.getController();
        menu.start(user);
        stage.show();
    }


     public void btnLibraryOnClick(ActionEvent event) throws IOException {
         FXMLLoader loader= new FXMLLoader(getClass().getResource("library.fxml"));
         Stage stage =(Stage) btnBack.getScene().getWindow();
         stage.setScene(new Scene(loader.load()));
         Library menu = loader.getController();
         menu.start(user);
         stage.show();
    }


    public void btnLogoutOnClick(ActionEvent event) throws IOException {
        Stage logout= (Stage) btnLogout.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login.fxml"));
        Scene scene= new Scene(fxmlLoader.load());
        logout.setScene(scene);
    }


    public void start(String user)
    {
        this.user= user;
        paneSlider.setTranslateX(-216);
        lblMenu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(paneSlider);

            slide.setToX(0);
            slide.play();

            paneSlider.setTranslateX(-216);

            slide.setOnFinished((ActionEvent e)-> {
                lblMenu.setVisible(false);
                lblMenuClose.setVisible(true);
            });
        });

        lblMenuClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(paneSlider);

            slide.setToX(-216);
            slide.play();

            paneSlider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)-> {
                lblMenu.setVisible(true);
                lblMenuClose.setVisible(false);
            });
        });

    }
}
