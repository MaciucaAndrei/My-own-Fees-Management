package project.licenta;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import project.licenta.entity.User;
import project.licenta.repository.UserRepository;
import project.licenta.service.UserService;
import project.licenta.utils.GetInstance;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.util.ArrayList;
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

    private UserService userService = GetInstance.of(UserService.class);

    public boolean FieldsValidation(String user, String pass)
    {
        if(!user.isBlank()  && !pass.isBlank()) {
            return true;
        }else
        {
            label.setText("Fill in all the fields");
            Paint paint = Paint.valueOf("red");
            label.setTextFill(paint);
            Font font = new Font("Gadugi",15);
            label.setFont(font);
            return false;
        }
    }

    public boolean LoginValidation(String user,String pass)
    {
        if(FieldsValidation(user,pass)) {
            List<User> users = userService.findAll();
            int flag = 0;
            for (User all : users) {
                if (all.getUsername().equals(user)) {
                    flag = 1;
                    if (all.getPassword()==pass.hashCode()) {

                        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                        a.setHeaderText("Autentificare cu succes!");
                        a.show();
                        return true;
                    } else {
                        label.setText("The password entered is incorrect");
                        Paint paint = Paint.valueOf("red");
                        label.setTextFill(paint);
                        Font font = new Font("Gadugi",15);
                        label.setFont(font);
                        return false;
                    }
                }

            }
            if (flag == 0) {
                label.setText("The username doesn't exist");
                Paint paint = Paint.valueOf("red");
                label.setTextFill(paint);
                Font font = new Font("Gadugi",15);
                label.setFont(font);
                return false;
            }
        }
        return false;
    }

    public void btnLoginOnClick(ActionEvent event) throws IOException
    {
        if(LoginValidation(txtUser.getText(),txtPass.getText()))
        {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("menu.fxml"));
            Stage stage =(Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            Menu menu = loader.getController();
            menu.start(txtUser.getText().toString());
            stage.show();

        }


    }
    public void btnRegisterOnClick(ActionEvent event) throws IOException {
        Stage login= (Stage) btnRegister.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("register.fxml"));
        Scene scene= new Scene(fxmlLoader.load());
        login.setScene(scene);

    }

    public void start(String user)
    {
        txtUser.setText(user);
    }
}
