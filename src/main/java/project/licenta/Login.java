package project.licenta;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

    private UserService userService = GetInstance.of(UserService.class);

    public boolean FieldsValidation(String user, String pass)
    {
        if(!user.isBlank()  && !pass.isBlank()) {
            return true;
        }else
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Completati toate campurile!");
            a.show();
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
                    if (all.getPassword().equals(pass)) {

                        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                        a.setHeaderText("Autentificare cu succes!");
                        a.show();
                        return true;
                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setHeaderText("Parola introdusa este incorecta!");
                        a.show();
                        return false;
                    }
                }

            }
            if (flag == 0) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Nu exista niciun utilizator cu acest nume de utlizator!");
                a.show();
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
}
