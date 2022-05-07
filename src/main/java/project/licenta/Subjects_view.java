package project.licenta;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import project.licenta.entity.Course;
import project.licenta.entity.Semester;
import project.licenta.entity.Subjects;
import project.licenta.service.SubjectsService;
import project.licenta.service.UserService;
import project.licenta.utils.GetInstance;

import javax.security.auth.Subject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Subjects_view {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnLogout;

    @FXML
    private AnchorPane paneButtons;

    @FXML
    private Label lblSemester;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnNewSubject;

    @FXML
    private TextField txtSubjectName;

    @FXML
    private TextField txtTeacher;

    @FXML
    private AnchorPane paneAdd;

    @FXML
    private ComboBox<String> cmbTeacher;

    @FXML
    private Button btnPlus;

    @FXML
    private Label lblTeachers;

    @FXML
    private Label label;

    private String user;
    private String semester;
    private SubjectsService subjectsService =GetInstance.of(SubjectsService .class);


    public void showButtons(String user, String semester)
    {
        List<Subjects> subjects = subjectsService.findAll();
        double x=30;
        double y=60;
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
        Stage stage =(Stage) btnAdd.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        Course_view course = loader.getController();
        course.start(subject);
        stage.show();
    }

    public void start(String user,String semester)
    {
        this.user= user;
        this.semester=semester;
        lblSemester.setText(semester);
        Font font = new Font("Gadugi", 20);
        lblSemester.setFont(font);
        Paint paint = Paint.valueOf("#d9e9f2");
        lblSemester.setTextFill(paint);
        showButtons(user,semester);

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

    public void btnAddOnClick(ActionEvent event)
    {
        paneAdd.setVisible(true);
        cmbTeacher.getItems().addAll("Course","Laboratory","Seminar");
    }

    public void btnPlusOnClick(ActionEvent event)
    {
        String text=txtTeacher.getText()+"/"+cmbTeacher.getValue()+"\n"+lblTeachers.getText();
        lblTeachers.setText(text);
        txtTeacher.clear();
    }

    public void btnNewSubjectOnClick(ActionEvent event)
    {
        int flag=0;
        if(!txtSubjectName.getText().isBlank() && !lblTeachers.getText().isBlank()) {
            List<Subjects> all = subjectsService.findAll();
            for (Subjects subjects : all) {
                if (txtSubjectName.getText().equals(subjects.getSubject_name())) {
                    flag = 1;
                    label.setText("This subject name already exists");
                    Paint paint = Paint.valueOf("red");
                    label.setTextFill(paint);
                    Font font = new Font("Gadugi", 15);
                    label.setFont(font);
                }

            }
        }else
        {
            label.setText("Fill in  the field");
            Paint paint = Paint.valueOf("red");
            label.setTextFill(paint);
            Font font = new Font("Gadugi",10);
            label.setFont(font);
        }
        if(flag==0)
        {
            String[]arrayTeachers=lblTeachers.getText().toString().split("\n");
            HashMap<String,String>hashTeachers= new HashMap<String,String>();
            for(String teacher : arrayTeachers)
            {
                String[] values= teacher.split("/");
                hashTeachers.put(values[1],values[0]);
            }
            Subjects subject = new Subjects(user,semester,txtSubjectName.getText(),hashTeachers);
            Subjects save = subjectsService.save(subject);
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("The subject has been successfully added");
            a.show();
            paneAdd.setVisible(false);
            txtSubjectName.clear();
            txtTeacher.clear();
            showButtons(user,semester);
        }
    }
}
