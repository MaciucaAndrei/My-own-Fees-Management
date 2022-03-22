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

public class Login {

    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPass;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnRegister;

    public void btnLoginOnClick(ActionEvent event)
    {
        if(txtUser.getText().toString().equals(txtPass.getText().toString()))
        {
            txtUser.setText("true");
        }
    }
    public void btnRegisterOnClick(ActionEvent event) throws IOException {
        Stage login= (Stage) btnRegister.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("register.fxml"));
        Scene scene= new Scene(fxmlLoader.load());
        login.setScene(scene);

    }
}
