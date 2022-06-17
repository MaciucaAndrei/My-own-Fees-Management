package project.licenta;

import at.favre.lib.crypto.bcrypt.BCrypt;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import project.licenta.entity.User;
import project.licenta.service.UserService;
import project.licenta.utils.GetInstance;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

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
    @FXML
    private Button btnLogin;
    @FXML
    private Label label;
    private final UserService userService = GetInstance.of(UserService.class);


    public boolean emailValidation(String e) {
        String emailRegex = "[a-zA-Z0-9_+&*-.]+@[a-zA-Z0-9_+&*-.]+[.][a-zA-Z0-9_+&*-]+";
        Pattern pat = Pattern.compile(emailRegex);
        if (pat.matcher(e).matches()) {
            return true;
        } else {
            label.setText("Email is invalid");
            Paint paint = Paint.valueOf("red");
            label.setTextFill(paint);
            Font font = new Font("Gadugi", 15);
            label.setFont(font);
            label.setLayoutX(75);
            label.setLayoutY(125);
            return false;
        }
    }

    public boolean passValidation(String p1, String p2) {
        if (p1.equals(p2)) {
            return true;
        } else {
            label.setText("Passwords doesn't match");
            Paint paint = Paint.valueOf("red");
            label.setTextFill(paint);
            Font font = new Font("Gadugi", 15);
            label.setFont(font);
            label.setLayoutX(75);
            label.setLayoutY(125);
            return false;
        }
    }

    public boolean fieldsValidation(String u, String e, String p1, String p2) {
        if (!u.isBlank() && !e.isBlank() && !p1.isBlank() && !p2.isBlank()) {
            return true;
        } else {
            label.setText("Fill in all the fields");
            Paint paint = Paint.valueOf("red");
            label.setTextFill(paint);
            Font font = new Font("Gadugi", 15);
            label.setFont(font);
            label.setLayoutX(75);
            label.setLayoutY(125);
            return false;
        }
    }

    public boolean registerValidation(String u, String e) {
        if (fieldsValidation(txtUser.getText(), txtEmail.getText(), txtPass.getText(), txtPassNew.getText()) &&
                emailValidation(txtEmail.getText()) && passValidation(txtPass.getText(), txtPassNew.getText())) {
            List<User> all = userService.findAll();
            for (User user : all) {
                if (user.getUsername().equals(u)) {
                    label.setText("Username already used");
                    Paint paint = Paint.valueOf("red");
                    label.setTextFill(paint);
                    Font font = new Font("Gadugi", 15);
                    label.setFont(font);
                    label.setLayoutX(75);
                    label.setLayoutY(125);
                    return false;
                }
                if (user.getEmail().equals(e)) {
                    label.setText("Email already used");
                    Paint paint = Paint.valueOf("red");
                    label.setTextFill(paint);
                    Font font = new Font("Gadugi", 15);
                    label.setFont(font);
                    label.setLayoutX(75);
                    label.setLayoutY(125);
                    return false;
                }

            }
            return true;
        }
        return false;
    }

    public void btnRegisterOnClick() throws IOException {
        if (registerValidation(txtUser.getText(), txtEmail.getText())) {
            String cryptPass = BCrypt.withDefaults().hashToString(12, txtPass.getText().toCharArray());
            User user = new User(txtUser.getText(), txtEmail.getText(), cryptPass);
            User save = userService.save(user);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Stage stage = (Stage) btnRegister.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            Login login = loader.getController();
            login.start(txtUser.getText());
            stage.show();
        }
    }

    public void btnLoginOnClick() throws IOException {
        Stage register = (Stage) btnLogin.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        register.setScene(scene);
    }


}
