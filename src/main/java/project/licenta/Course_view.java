package project.licenta;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import project.licenta.entity.Subjects;
import project.licenta.service.SubjectsService;
import project.licenta.utils.GetInstance;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Course_view {
    @FXML
    private Label lblSemester;
    @FXML
    private Label lblCourse;
    @FXML
    private Label lblTeachers;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnLogout;
    @FXML
    private AnchorPane paneButtons;

    private String user;
    private SubjectsService subjectsService = GetInstance.of(SubjectsService.class);

    public void showButtons(String user, String semester)
    {
        List<Subjects> subjects = subjectsService.findAll();
        double x=25;
        double y=172;
        for(Subjects subject : subjects) {
            if (user.equals(subject.getUser()) && semester.equals(subject.getSemester())) {
                Button button = new Button();
                button.setLayoutX(x);
                button.setLayoutY(y);
                button.setText(subject.getSubject_name());
                y = y + 45;
                Font font = new Font("Gadugi", 15);
                button.setFont(font);
                Paint paint = Paint.valueOf("#d9e9f2");
                button.setTextFill(paint);
                button.setStyle("-fx-background-color: transparent");
                button.setOnAction(e-> {
                    try {
                        buttonOnClick(subject);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                paneButtons.getChildren().add(button);
            }
        }
    }

    public void buttonOnClick(Subjects subject) throws IOException
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("course_view.fxml"));
        Stage stage =(Stage) btnBack.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        Course_view course = loader.getController();
        course.start(subject);
        stage.show();
    }



    public void start(Subjects subject)
    {
        this.user = subject.getUser();
        lblSemester.setText(subject.getSemester());
        lblCourse.setText(subject.getSubject_name());


    }

    public void btnBackOnClick(ActionEvent event) throws IOException
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("menu.fxml"));
        Stage stage =(Stage) btnBack.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        Menu menu = loader.getController();
        menu.start(user);
        stage.show();
    }
    public void btnLogoutOnClick(ActionEvent event) throws  IOException
    {
        Stage login= (Stage) btnLogout.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login.fxml"));
        Scene scene= new Scene(fxmlLoader.load());
        login.setScene(scene);
    }
}
