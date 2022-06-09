package project.licenta;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
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
    private AnchorPane paneMenu;
    @FXML
    private ScrollPane scpaneButtons;
    @FXML
    private ImageView image1;
    @FXML
    private ImageView image2;
    @FXML
    private ImageView image3;

    private String user;
    private String semester_text;
    private Semester semester;
    private final List<ComboBox<String>> previousyears = new ArrayList<>();
    private final SubjectsService subjectsService = GetInstance.of(SubjectsService.class);
    private final AllSubjectsService allSubjectsService = GetInstance.of(AllSubjectsService.class);


    public void showButtons(String user, String semester) {
        List<Subjects> subjects = subjectsService.findAll();
        double x = 30;
        double y = 80;
        for (Subjects subject : subjects) {
            if (user.equals(subject.getUser()) && semester.equals(subject.getSemester())) {
                Button button = new Button();
                button.setLayoutX(x);
                button.setLayoutY(y);
                button.setText(subject.getSubject_name());
                y = y + 85;
                Font font = new Font("Gadugi", 15);
                button.setFont(font);
                Paint paint = Paint.valueOf("#d9e9f2");
                button.setTextFill(paint);
                button.setStyle("-fx-background-color: transparent;  -fx-border-width: 3px; -fx-border-color: #d9e9f2;");
                button.setOnMouseEntered(e -> {
                    button.setStyle("-fx-background-color: transparent;-fx-text-fill:#000080; -fx-border-width: 3px;" +
                            "-fx-border-color: #000080;");
                    button.setTranslateX(5);
                    button.setTranslateY(-10);
                });
                button.setOnMouseExited(e -> {
                    button.setStyle("-fx-background-color: transparent;-fx-text-fill: #d9e9f2; -fx-border-width: 3px;" +
                            "-fx-border-color:#d9e9f2;");
                    button.setTranslateX(-5);
                    button.setTranslateY(10);
                });
                button.setOnAction(e -> {
                    try {
                        buttonOnClick(subject, this.semester);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                paneButtons.getChildren().add(button);
            }
            if (y > scpaneButtons.getWidth()) {
                scpaneButtons.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            }
        }
    }

    public void buttonOnClick(Subjects subject, Semester semester) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("course_view.fxml"));
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        Course_view course = loader.getController();
        course.start(subject, semester);
        stage.show();
    }

    public void start(String user, String semester, Semester s) {
        this.user = user;
        this.semester_text = semester;
        this.semester = s;
        lblSemester.setText(semester);
        Font font = new Font("Gadugi", 20);
        lblSemester.setFont(font);
        Paint paint = Paint.valueOf("#d9e9f2");
        lblSemester.setTextFill(paint);
        showButtons(user, semester);
        if (s.getYear() == 1) {
            btnPrevious.setVisible(false);
        }
        if (s.getTaxes()) {
            btnSemesterfee.setVisible(true);
        }
        paneSlider.setTranslateX(-257);
        lblMenu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(paneSlider);

            slide.setToX(0);
            slide.play();

            paneSlider.setTranslateX(-257);

            slide.setOnFinished((ActionEvent e) -> {
                lblMenu.setVisible(false);
                lblMenuClose.setVisible(true);
            });
        });

        lblMenuClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(paneSlider);

            slide.setToX(-257);
            slide.play();

            paneSlider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e) -> {
                lblMenu.setVisible(true);
                lblMenuClose.setVisible(false);
            });
        });
        for (Node node : paneMenu.getChildren()) {
            if (node.getClass().equals(Button.class)) {
                node.setOnMouseEntered(e -> {
                    node.setStyle("-fx-background-color: transparent;-fx-text-fill:#000080;");
                    node.setTranslateX(5);
                });
                node.setOnMouseExited(e -> {
                    node.setStyle("-fx-background-color: transparent;-fx-text-fill: #d9e9f2;");
                    node.setTranslateX(-5);
                });
            }
        }
        btnActual.setOnMouseEntered(e -> {
            btnActual.setStyle("-fx-background-color: transparent;-fx-text-fill:#66b3ff; -fx-border-width: 3px;" +
                    "-fx-border-color: #66b3ff;");
            btnActual.setTranslateX(5);
            btnActual.setTranslateY(-5);
            javafx.scene.image.Image image = null;
            try {
                image = new javafx.scene.image.Image(Library.class.getResource("images/subject_blue.png").openStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            image1.setImage(image);
        });
        btnActual.setOnMouseExited(e -> {
            btnActual.setStyle("-fx-background-color: transparent;-fx-text-fill: #d9e9f2; -fx-border-width: 3px;" +
                    "-fx-border-color:#d9e9f2;");
            btnActual.setTranslateX(-5);
            btnActual.setTranslateY(5);
            javafx.scene.image.Image image = null;
            try {
                image = new javafx.scene.image.Image(Library.class.getResource("images/subject_white.png").openStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            image1.setImage(image);
        });
        btnOptional.setOnMouseEntered(e -> {
            btnOptional.setStyle("-fx-background-color: transparent;-fx-text-fill:#66b3ff; -fx-border-width: 3px;" +
                    "-fx-border-color: #66b3ff;");
            btnOptional.setTranslateX(5);
            btnOptional.setTranslateY(-5);
            javafx.scene.image.Image image = null;
            try {
                image = new javafx.scene.image.Image(Library.class.getResource("images/subject_blue.png").openStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            image2.setImage(image);
        });
        btnOptional.setOnMouseExited(e -> {
            btnOptional.setStyle("-fx-background-color: transparent;-fx-text-fill: #d9e9f2; -fx-border-width: 3px;" +
                    "-fx-border-color:#d9e9f2;");
            btnOptional.setTranslateX(-5);
            btnOptional.setTranslateY(5);
            javafx.scene.image.Image image = null;
            try {
                image = new javafx.scene.image.Image(Library.class.getResource("images/subject_white.png").openStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            image2.setImage(image);
        });
        btnPrevious.setOnMouseEntered(e -> {
            btnPrevious.setStyle("-fx-background-color: transparent;-fx-text-fill:#66b3ff; -fx-border-width: 3px;" +
                    "-fx-border-color: #66b3ff;");
            btnPrevious.setTranslateX(5);
            btnPrevious.setTranslateY(-5);
            javafx.scene.image.Image image = null;
            try {
                image = new javafx.scene.image.Image(Library.class.getResource("images/subject_blue.png").openStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            image3.setImage(image);
        });
        btnPrevious.setOnMouseExited(e -> {
            btnPrevious.setStyle("-fx-background-color: transparent;-fx-text-fill: #d9e9f2; -fx-border-width: 3px;" +
                    "-fx-border-color:#d9e9f2;");
            btnPrevious.setTranslateX(-5);
            btnPrevious.setTranslateY(5);
            javafx.scene.image.Image image = null;
            try {
                image = new javafx.scene.image.Image(Library.class.getResource("images/subject_white.png").openStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            image3.setImage(image);
        });
    }

    public void btnBackOnClick() throws IOException, AWTException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        Menu menu = loader.getController();
        menu.start(user);
        stage.show();
    }

    public void btnLogoutOnClick() throws IOException {
        File path = FileUtils.getUserDirectory().getAbsoluteFile();
        File file = new File(path.getAbsolutePath() + File.separator + "user.txt");
        FileWriter writer = new FileWriter(file.getAbsolutePath());
        writer.write(user + ";" + "false");
        writer.close();
        Stage login = (Stage) btnLogout.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        login.setScene(scene);
    }

    public void optionsCombo(String text, ComboBox<String> combo, int year, int semester) {
        List<AllSubjects> all = allSubjectsService.findAll();

        for (AllSubjects subject : all) {
            if (subject.getCode().equals(text) && subject.getYear() == year && subject.getSemester() == semester) {
                combo.getItems().add(subject.getSubject_name());
            }
        }

    }

    public boolean subjectValidation(Label label, String name) {
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


    public void btnActualOnClick() {
        paneAdd.setVisible(true);
        String text = semester_text.substring(4, 6) + "." + semester.getDepartment().charAt(0);
        optionsCombo(text, cmbSubject, semester.getYear(), semester.getSemester());
    }

    public void btnOptionalOnClick() {
        paneAdd.setVisible(true);
        String text = semester_text.substring(4, 6) + ".O";
        optionsCombo(text, cmbSubject, semester.getYear(), semester.getSemester());
    }

    public void btnPreviousOnClick() {
        panePrevious.setVisible(true);
        List<AllSubjects> all = allSubjectsService.findAll();
        String principal = semester_text.substring(4, 6) + "." + semester.getDepartment().charAt(0);
        String optional = semester_text.substring(4, 6) + ".O";
        double y = 40;
        for (int i = 1; i < semester.getYear(); i++) {
            AnchorPane pane = new AnchorPane();
            pane.setPrefWidth(256);
            pane.setPrefHeight(35);
            pane.setLayoutX(65);
            pane.setLayoutY(y);
            y = y + 50;
            pane.setStyle("-fx-background-color: #003d66");
            Label label = new Label();
            label.setText("Year " + i);
            label.setLayoutX(8);
            label.setLayoutY(5);
            Paint paint = Paint.valueOf("#d9e9f2");
            label.setTextFill(paint);
            Font font = new Font("Gadugi", 15);
            label.setFont(font);
            pane.getChildren().add(label);
            ComboBox<String> combo = new ComboBox<>();
            combo.setPrefWidth(241);
            combo.setPrefHeight(26);
            combo.setLayoutX(49);
            combo.setLayoutY(1);
            combo.setPromptText("subjects name");
            for (AllSubjects subjects : all) {
                if ((subjects.getCode().equals(principal) || subjects.getCode().equals(optional)) && subjects.getYear() == i
                        && subjects.getSemester() == semester.getSemester()) {
                    combo.getItems().add(subjects.getSubject_name());
                }
            }
            previousyears.add(combo);
            pane.getChildren().add(combo);
            panePrevious.getChildren().add(pane);
        }

    }

    public void btnNewSubjectOnClick() {
        if (subjectValidation(lblNew, cmbSubject.getValue())) {
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

    public void btnPreviousSubjectOnClick() {
        for (ComboBox<String> cmb : previousyears) {
            if (cmb.getValue() != null) {
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

    public void btnSemesterfeeOnClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("semesterfee.fxml"));
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        Semesterfee menu = loader.getController();
        menu.start(user, semester_text, semester);
        stage.show();
    }


}
