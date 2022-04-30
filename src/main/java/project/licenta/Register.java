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
import project.licenta.entity.User;
import project.licenta.service.UserService;
import project.licenta.utils.GetInstance;
import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

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



    public boolean EmailValidation(String e)
    {
        String emailRegex="[a-zA-Z0-9_+&*-.]+@[a-zA-Z0-9_+&*-.]+[.][a-zA-Z0-9_+&*-]+";
        Pattern pat = Pattern.compile(emailRegex);
         if(pat.matcher(e).matches())
         {
             return true;
         }else
         {
             Alert a= new Alert(Alert.AlertType.ERROR);
             a.setHeaderText("Emailul este invalid");
             a.show();
             return false;
         }
    }

    public boolean PassValidation(String p1, String p2) {
        if (p1.equals(p2)) {
            return true;
        }else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Parolele nu coincid!");
            a.show();
            return false;
        }
    }

    public boolean FieldsValidation(String u,String e, String p1, String p2)
    {
        if(!u.isBlank() && !e.isBlank() && !p1.isBlank() && !p2.isBlank()) {
            return true;
        }else
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Completati toate campurile!");
            a.show();
            return false;
        }
    }

    public boolean RegisterValidation(String u, String e ){
        if(FieldsValidation(txtUser.getText(),txtEmail.getText(),txtPass.getText(),txtPassNew.getText())&&
        EmailValidation(txtEmail.getText()) && PassValidation(txtPass.getText(),txtPassNew.getText()))
        {
            List<User> all = userService.findAll();
            for(User user : all)
            {
                if(user.getUsername().equals(u))
                {
                    Alert a= new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("Nume de utilizator deja existent!");
                    a.show();
                    return false;
                }
                if(user.getEmail().equals(e))
                {
                    Alert a= new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("Email deja existent!");
                    a.show();
                    return false;
                }

            }
            User user = new User(txtUser.getText(), txtEmail.getText(), txtPass.getText());
            User save = userService.save(user);
            Alert a= new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("Contul a fost inregistrat cu succes!");
            a.show();
            return true;
        }
        return false;
    }

    public void btnRegisterOnClick(ActionEvent event) throws IOException {
        if (RegisterValidation(txtUser.getText(),txtEmail.getText())) {
            Stage register = (Stage) btnRegister.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            register.setScene(scene);
        }
    }


}
