package project.licenta;

import at.favre.lib.crypto.bcrypt.BCrypt;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import project.licenta.entity.User;
import project.licenta.notifications.NotificationObservable;
import project.licenta.notifications.NotificationObserver;
import project.licenta.service.UserService;
import project.licenta.utils.GetInstance;

import javax.enterprise.context.ApplicationScoped;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

@ApplicationScoped
public class Login {

    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPass;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnRegister;
    @FXML
    private Label label;
    @FXML
    private CheckBox chkLogged;

    private final UserService userService = GetInstance.of(UserService.class);
    private final NotificationObservable notificationObservable = new NotificationObservable();
    private final NotificationObserver notificationObserver = new NotificationObserver();

    public boolean fieldsValidation(String user, String pass) {
        if (!user.isBlank() && !pass.isBlank()) {
            return true;
        } else {
            label.setText("Fill in all the fields");
            Paint paint = Paint.valueOf("red");
            label.setTextFill(paint);
            Font font = new Font("Gadugi", 15);
            label.setFont(font);
            return false;
        }
    }

    public boolean loginValidation(String user, String pass) {
        if (fieldsValidation(user, pass)) {
            List<User> users = userService.findAll();
            for (User all : users) {
                if (all.getUsername().equals(user)) {
                    BCrypt.Result result = BCrypt.verifyer().verify(pass.toCharArray(), all.getPassword());
                    if (result.verified) {
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                        a.setHeaderText("Login successful!");
                        a.show();
                        return true;
                    } else {
                        label.setText("The password entered is incorrect");
                        Paint paint = Paint.valueOf("red");
                        label.setTextFill(paint);
                        Font font = new Font("Gadugi", 15);
                        label.setFont(font);
                        return false;
                    }
                }

            }
            label.setText("The username doesn't exist");
            Paint paint = Paint.valueOf("red");
            label.setTextFill(paint);
            Font font = new Font("Gadugi", 15);
            label.setFont(font);
            return false;
        }
        return false;
    }

    public void btnLoginOnClick() throws IOException, AWTException {
        if (loginValidation(txtUser.getText(), txtPass.getText())) {
            File path = FileUtils.getUserDirectory().getAbsoluteFile();
            File file = new File(path.getAbsolutePath() + File.separator + "user.txt");
            FileWriter writer = new FileWriter(file.getAbsolutePath());
            writer.write(txtUser.getText() + ";" + chkLogged.isSelected());
            writer.close();
            notificationObservable.addObserver(notificationObserver);
            notificationObservable.setUser(txtUser.getText());
            notificationObservable.setToday(Calendar.getInstance());
            Thread th = new Thread(notificationObservable);
            th.setDaemon(true);
            th.start();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            Menu menu = loader.getController();
            menu.start(txtUser.getText());
            stage.show();

        }


    }

    public void btnRegisterOnClick() throws IOException {
        Stage login = (Stage) btnRegister.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("register.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        login.setScene(scene);

    }

    public void start(String user) {
        txtUser.setText(user);
    }
}
