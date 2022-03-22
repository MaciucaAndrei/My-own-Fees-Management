package project.licenta;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Register {


    @FXML
    private Button btnRegister;
    @FXML
    private TextField txtUser;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPass;
    @FXML
    private PasswordField txtPassNew;



    public boolean Passvalidation(String p1, String p2) {
        if (p1.equals(p2)) {
            return true;
        }
        return false;
    }

    public void btnRegisterOnClick(ActionEvent event) throws IOException {


        if (Passvalidation(txtPass.getText(),txtPassNew.getText()))
        {
            Stage register = (Stage) btnRegister.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            register.setScene(scene);
        }
    }


}
