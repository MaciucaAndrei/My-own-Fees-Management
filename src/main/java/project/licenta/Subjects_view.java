package project.licenta;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.io.FileUtils;
import project.licenta.entity.AllSubjects;
import project.licenta.entity.Semester;
import project.licenta.entity.Subjects;
import project.licenta.service.AllSubjectsService;
import project.licenta.service.SubjectsService;
import project.licenta.utils.GetInstance;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
    private Button btnActual;

    @FXML
    private Button btnPrevious;

    @FXML
    private Button btnOptional;

    @FXML
    private Button btnNewSubject;

    @FXML
    private Button btnPreviousSubject;

    @FXML
    private AnchorPane paneAdd;

    @FXML
    private AnchorPane panePrevious;

    @FXML
    private ComboBox<String> cmbSubject;

    @FXML
    private Button btnSemesterfee;

    @FXML
    private Label lblNew;

    @FXML
    private Label lblPrevious;
    @FXML
    private Label lblMenu;
    @FXML
    private Label lblMenuClose;
    @FXML
    private AnchorPane paneSlider;
    @FXML
    private ScrollPane scpaneButtons;

    private String user;
    private String semester_text;
    private Semester semester;
    private List<ComboBox<String>> previousyears = new ArrayList<ComboBox<String>>();
    private SubjectsService subjectsService =GetInstance.of(SubjectsService .class);
    private AllSubjectsService allSubjectsService =GetInstance.of(AllSubjectsService .class);


    public void showButtons(String user, String semester)
    {
        List<Subjects> subjects = subjectsService.findAll();
        double x=30;
        double y=80;
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
                        buttonOnClick(subject,this.semester);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                paneButtons.getChildren().add(button);
            }
            if(y>scpaneButtons.getWidth())
            {
               scpaneButtons.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            }
        }
    }

    public void buttonOnClick(Subjects subject,Semester semester) throws IOException
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("course_view.fxml"));
        Stage stage =(Stage) btnBack.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        Course_view course = loader.getController();
        course.start(subject,semester);
        stage.show();
    }

    public void start(String user,String semester,Semester s)
    {
        this.user= user;
        this.semester_text=semester;
        this.semester=s;
        lblSemester.setText(semester);
        Font font = new Font("Gadugi", 20);
        lblSemester.setFont(font);
        Paint paint = Paint.valueOf("#d9e9f2");
        lblSemester.setTextFill(paint);
        showButtons(user,semester);
        if(s.getTaxes())
        {
            btnSemesterfee.setVisible(true);
        }
        paneSlider.setTranslateX(-303);
        lblMenu.setOnMouseClicked(event -> {
            scpaneButtons.setVisible(false);
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(paneSlider);

            slide.setToX(0);
            slide.play();

            paneSlider.setTranslateX(-303);

            slide.setOnFinished((ActionEvent e)-> {
                lblMenu.setVisible(false);
                lblMenuClose.setVisible(true);
            });
        });

        lblMenuClose.setOnMouseClicked(event -> {
            scpaneButtons.setVisible(true);
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(paneSlider);

            slide.setToX(-303);
            slide.play();

            paneSlider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)-> {
                lblMenu.setVisible(true);
                lblMenuClose.setVisible(false);
            });
        });
    }

    public void btnBackOnClick(ActionEvent event) throws IOException, AWTException
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
        File path = FileUtils.getUserDirectory().getAbsoluteFile();
        File file = new File(path.getAbsolutePath()+File.separator+"user.txt");
        FileWriter writer = new FileWriter(file.getAbsolutePath());
        writer.write(user+";"+"false");
        writer.close();
        Stage login= (Stage) btnLogout.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login.fxml"));
        Scene scene= new Scene(fxmlLoader.load());
        login.setScene(scene);
    }

    public void optionsCombo(String text,ComboBox<String> combo,int year, int semester)
    {
        List<AllSubjects> all = allSubjectsService.findAll();

        for(AllSubjects subject : all)
        {
            if(subject.getCode().equals(text)&&subject.getYear()==year&&subject.getSemester()==semester)
            {
                combo.getItems().add(subject.getSubject_name());
            }
        }

    }
    public boolean subjectValidation(Label label,String name) {
        List<Subjects> all = subjectsService.findAll();
        if (name.isBlank()) {
            label.setText("Fill in  the field");
            Paint paint = Paint.valueOf("red");
            label.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
            label.setFont(font);
            return false;
        }
        for (Subjects subjects : all) {
            if (subjects.getUser().equals(user) && subjects.getSemester().equals(semester_text) && subjects.getSubject_name().equals(name)) {
                label.setText("This subject has been already added");
                Paint paint = Paint.valueOf("red");
                label.setTextFill(paint);
                Font font = new Font("Gadugi", 10);
                label.setFont(font);
                return false;
            }
        }
        return true;
    }



    public void btnActualOnClick(ActionEvent e)
    {
        paneAdd.setVisible(true);
        String text= semester_text.substring(4,6)+"."+semester.getDepartment().substring(0,1);
        optionsCombo(text,cmbSubject,semester.getYear(),semester.getSemester());
    }

    public void btnOptionalOnClick(ActionEvent e)
    {
        paneAdd.setVisible(true);
        String text= semester_text.substring(4,6)+".O";
        optionsCombo(text,cmbSubject,semester.getYear(),semester.getSemester());
    }

    public void btnPreviousOnClick(ActionEvent e)
    {
        panePrevious.setVisible(true);
        List<AllSubjects>all  = allSubjectsService.findAll();
        String principal= semester_text.substring(4,6)+"."+semester.getDepartment().substring(0,1);
        String optional = semester_text.substring(4,6)+".O";
        double y= 40;
        for(int i=1;i<semester.getYear();i++)
        {
            AnchorPane pane = new AnchorPane();
            pane.setPrefWidth(256);
            pane.setPrefHeight(35);
            pane.setLayoutX(65);
            pane.setLayoutY(y);
            y=y+50;
            pane.setStyle("-fx-background-color: #003d66");
            Label label = new Label();
            label.setText("Year "+i);
            label.setLayoutX(8);
            label.setLayoutY(5);
            Paint paint = Paint.valueOf("#d9e9f2");
            label.setTextFill(paint);
            Font font = new Font("Gadugi", 15);
            label.setFont(font);
            pane.getChildren().add(label);
            ComboBox<String> combo = new ComboBox<String>();
            combo.setPrefWidth(241);
            combo.setPrefHeight(26);
            combo.setLayoutX(49);
            combo.setLayoutY(1);
            combo.setPromptText("subjects name");
            for(AllSubjects subjects : all)
            {
                if((subjects.getCode().equals(principal)||subjects.getCode().equals(optional)) && subjects.getYear()==i
                        && subjects.getSemester()==semester.getSemester())
                {
                    combo.getItems().add(subjects.getSubject_name());
                }
            }
            previousyears.add(combo);
            pane.getChildren().add(combo);
            panePrevious.getChildren().add(pane);
        }

    }

    public void btnNewSubjectOnClick(ActionEvent event)
    {
        if(subjectValidation(lblNew,cmbSubject.getValue()))
        {
            Subjects newSubject = new Subjects(user, semester_text, cmbSubject.getValue());
            Subjects save = subjectsService.save(newSubject);
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("The subject has been successfully added");
            a.show();
            paneAdd.setVisible(false);
            lblNew.setText("");
            cmbSubject.getItems().clear();
            showButtons(user, semester_text);
        }

    }

    public void btnPreviousSubjectOnClick(ActionEvent event)
    {
        for(ComboBox<String> cmb : previousyears) {
           if(cmb.getValue()!=null) {
               if (subjectValidation(lblPrevious, cmb.getValue())) {
                   Subjects newSubject = new Subjects(user, semester_text, cmb.getValue());
                   Subjects save = subjectsService.save(newSubject);
                   Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                   a.setHeaderText("The subject has been successfully added");
                   a.show();
                   panePrevious.setVisible(false);
                   lblPrevious.setText("");
                   previousyears.clear();
                   showButtons(user, semester_text);
               }
           }
        }
    }

    public void btnSemesterfeeOnClick(ActionEvent e) throws IOException
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("semesterfee.fxml"));
        Stage stage =(Stage) btnBack.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        Semesterfee menu = loader.getController();
        menu.start(user,semester_text,semester);
        stage.show();
    }




}
