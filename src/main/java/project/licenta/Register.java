package project.licenta;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.licenta.entity.User;
import project.licenta.service.UserService;
import project.licenta.utils.GetInstance;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;

@ApplicationScoped
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

    private UserService userService = GetInstance.of(UserService.class);


    public boolean Passvalidation(String p1, String p2) {
        if (p1.equals(p2)) {
            return true;
        }
        return false;
    }

    public void btnRegisterOnClick(ActionEvent event) throws IOException {

        String username = txtUser.getText();
        String email = txtEmail.getText();
        String password = txtPass.getText();

        User user = new User(username, email, password);

        User save = userService.save(user);

        if (Passvalidation(txtPass.getText(), txtPassNew.getText())) {
            Stage register = (Stage) btnRegister.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            register.setScene(scene);
        }
    }


}
