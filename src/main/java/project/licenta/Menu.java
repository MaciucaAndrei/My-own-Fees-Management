package project.licenta;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import project.licenta.entity.Semester;
import project.licenta.service.SemesterService;
import project.licenta.utils.GetInstance;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class Menu {
    private String user;

    @FXML
    private TextField txtUniv;

    @FXML
    private TextField txtColl;

    @FXML
    private TextField txtDep;

    @FXML
    private  TextField txtUniv_year;

    @FXML
    private Spinner<Integer> spnYear;

    @FXML
    private Spinner<Integer> spnSem;

    @FXML
    private CheckBox chbTaxes;

    @FXML
    private Button btnLibrary;

    @FXML
    private Button btnCosts;

    @FXML
    private Button btnGraphic;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnLogout;

    @FXML
    private AnchorPane paneSemesterAdd;

    @FXML
    private AnchorPane paneButtons;

    @FXML
    private AnchorPane paneMenu;
    @FXML
    private ScrollPane scpaneButtons;

    @FXML
    private Label label;

    private final SemesterService semesterService= GetInstance.of(SemesterService .class);


    public String buttonText(Semester semester)
    {
        StringBuilder text= new StringBuilder();
        if(semester.getUniversity().length()<5)
        {
            text = new StringBuilder(semester.getUniversity() + " ");
        }else
        {
            String[] shortcuts=semester.getUniversity().split(" ");
            if(shortcuts.length==1)
            {
                text = new StringBuilder(shortcuts[0].substring(0, 4));
            }else {
                for (String shortcut : shortcuts) {
                    if(shortcut.length()>3) {
                        text.append(shortcut.charAt(0));
                    }
                }
            }
            text.append(" ");

        }

        if(semester.getCollege().length()<5)
        {
            text.append(semester.getCollege()).append(" ");
        }else
        {
            String[] shortcuts=semester.getCollege().split(" ");
            if(shortcuts.length==1)
            {
                text.append(shortcuts[0], 0, 4);
            }else {
                for (String shortcut : shortcuts) {
                    if(shortcut.length()>3) {
                        text.append(shortcut.charAt(0));
                    }
                }
            }
            text.append(" ");
        }

        if(semester.getDepartment().length()<5)
        {
            text.append(semester.getDepartment()).append(" ");
        }else
        {
            String[] shortcuts=semester.getDepartment().split(" ");
            if(shortcuts.length==1)
            {
                text.append(shortcuts[0], 0, 4);
            }else {
                for (String shortcut : shortcuts) {
                    if(shortcut.length()>3) {
                        text.append(shortcut.charAt(0));
                    }
                }
            }
            text.append(" ");
        }
        text.append("\n").append("An universitar: ").append(semester.getUniversity_year()).append(" An: ").append(semester.getYear()).append(" Semestru: ").append(semester.getSemester());
        return text.toString();
    }

    public void showButtons(String user)
    {
        List<Semester> semesters = semesterService.findAll();
        double x=20;
        double y=50;
        for(Semester semester : semesters)
        {
            if(user.equals(semester.getUser())) {
                Button button = new Button();
                button.setLayoutX(x);
                button.setLayoutY(y);
                button.setText(buttonText(semester));
                y = y + 85;
                Font font= new Font("Gadugi",15);
                button.setFont(font);
                Paint paint = Paint.valueOf("#d9e9f2");
                button.setTextFill(paint);
                button.setStyle("-fx-background-color: transparent; -fx-border-width: 3px; -fx-border-color: #d9e9f2;");
                button.setOnMouseEntered(e->{
                    button.setStyle("-fx-background-color: transparent;-fx-text-fill:#000080; -fx-border-width: 3px;" +
                            "-fx-border-color: #000080;");
                    button.setTranslateX(5);
                    button.setTranslateY(-10);
                });
                button.setOnMouseExited(e->{
                    button.setStyle("-fx-background-color: transparent;-fx-text-fill: #d9e9f2; -fx-border-width: 3px;" +
                            "-fx-border-color:#d9e9f2;");
                    button.setTranslateX(-5);
                    button.setTranslateY(10);
                });
                button.setOnAction(e-> {
                    try {
                        Semesters_view(button.getText(),semester);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                paneButtons.getChildren().add(button);
            }
        }
        if(y>paneButtons.getPrefHeight())
        {

            scpaneButtons.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        }
    }

    public void Semesters_view(String semester,Semester s) throws IOException
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("subjects_view.fxml"));
        Stage stage =(Stage) btnAdd.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        Subjects_view menu = loader.getController();
        menu.start(user,semester,s);
        stage.show();
    }


    public void start(String user) throws AWTException
    {
        this.user= user;
        SpinnerValueFactory<Integer> yearFactory =new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4, 1);
        spnYear.setValueFactory(yearFactory);
        SpinnerValueFactory<Integer> semFactory =new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 2, 1);
        spnSem.setValueFactory(semFactory);
        showButtons(user);
        for(Node node : paneMenu.getChildren())
            if(node.getClass().equals(Button.class)) {
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

    public boolean FieldsValidation(String u,String c, String d, String y)
    {
        if(!u.isBlank() && !c.isBlank() && !d.isBlank() && !y.isBlank()) {
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
    public boolean SemesterValidation(Semester duplicate)
    {
        List<Semester> semesters = semesterService.findAll();
        String duplicate_shortcuts= buttonText(duplicate);
        for(Semester semester : semesters)
        {
           String semester_shortcuts = buttonText(semester);
           if(semester.getUser().equals(user) && duplicate_shortcuts.equals(semester_shortcuts))
           {
               label.setText("This semester was already added");
               Paint paint = Paint.valueOf("red");
               label.setTextFill(paint);
               Font font = new Font("Gadugi",15);
               label.setFont(font);
               return false;
           }
           if(semester.getUniversity().equalsIgnoreCase(duplicate.getUniversity()) &&
                   semester.getCollege().equalsIgnoreCase(duplicate.getCollege()) &&
                   semester.getDepartment().equalsIgnoreCase(duplicate.getDepartment()) &&
                   semester.getUniversity_year().equals(duplicate.getUniversity_year()) &&
                   semester.getYear()==duplicate.getYear() && semester.getSemester()==duplicate.getSemester())
           {
               label.setText("This semester was already added");
               Paint paint = Paint.valueOf("red");
               label.setTextFill(paint);
               Font font = new Font("Gadugi",15);
               label.setFont(font);
               return false;
           }
        }
        return true;
    }

    public boolean Univ_yearValidation(String year)
    {
        if(year.contains("-"))
        {
            String[] years= year.split("-");
            int year1= Integer.parseInt(years[0]);
            int year2= Integer.parseInt(years[1]);
            Calendar c= Calendar.getInstance();
            if(year1<year2)
            {
                if(year1<=c.get(Calendar.YEAR)&&year2<=(c.get(Calendar.YEAR)+1))
                {
                    return true;
                }else
                {
                    label.setText("The university year cannot be from the future");
                    Paint paint = Paint.valueOf("red");
                    label.setTextFill(paint);
                    Font font = new Font("Gadugi",15);
                    label.setFont(font);
                    return false;
                }

            }else
            {
                label.setText("The university year must respect the chronological order");
                Paint paint = Paint.valueOf("red");
                label.setTextFill(paint);
                Font font = new Font("Gadugi",15);
                label.setFont(font);
                return false;
            }
        }else
        {
            label.setText("The university year must be in the form: 2000-2001");
            Paint paint = Paint.valueOf("red");
            label.setTextFill(paint);
            Font font = new Font("Gadugi",15);
            label.setFont(font);
            return false;
        }
    }


    public void btnGraphicOnClick() throws IOException
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("graphic.fxml"));
        Stage stage =(Stage) btnGraphic.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        Graphic menu = loader.getController();
        menu.start(user);
        stage.show();
    }

    public void btnLogoutOnClick() throws IOException
    {
        File path = FileUtils.getUserDirectory().getAbsoluteFile();
        File file = new File(path.getAbsolutePath()+File.separator+"user.txt");
        FileWriter writer = new FileWriter(file.getAbsolutePath());
        writer.write(user+";"+"false");
        writer.close();
        Stage logout= (Stage) btnLogout.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login.fxml"));
        Scene scene= new Scene(fxmlLoader.load());
        logout.setScene(scene);
    }
    public void btnAddSemesterOnClick()
    {
        paneSemesterAdd.setVisible(true);
        List<Semester> all = semesterService.findAll();
        for(Semester semester : all)
        {
            if(semester.getUser().equals(this.user))
            {
                txtUniv.setText(semester.getUniversity());
                txtColl.setText(semester.getCollege());
                txtDep.setText(semester.getDepartment());
                txtUniv_year.setText(semester.getUniversity_year());
            }
        }
    }
    public void btnAddOnClick()
    {
        if(FieldsValidation(txtUniv.getText(), txtColl.getText(), txtDep.getText()
                , txtUniv_year.getText())&&Univ_yearValidation(txtUniv_year.getText()))
        {
            Semester semester= new Semester(user, txtUniv.getText(), txtColl.getText()
                    , txtDep.getText(), txtUniv_year.getText(),spnYear.getValue(),spnSem.getValue(),chbTaxes.isSelected());
            if(SemesterValidation(semester)) {
                Semester save = semesterService.save(semester);
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setHeaderText("The semester has been successfully added");
                a.show();
                label.setText("");
                paneSemesterAdd.setVisible(false);
                txtUniv.clear();
                txtColl.clear();
                txtDep.clear();
                txtUniv_year.clear();
                spnYear.getValueFactory().setValue(1);
                spnSem.getValueFactory().setValue(1);
                paneButtons.getChildren().clear();
                showButtons(user);
            }
        }
    }

    public void btnLibraryOnClick() throws IOException
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("library.fxml"));
        Stage stage =(Stage) btnLibrary.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        Library menu = loader.getController();
        menu.start(user);
        stage.show();
    }

    public void btnCostsOnClick() throws IOException
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("costs.fxml"));
        Stage stage =(Stage) btnCosts.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        Costs menu = loader.getController();
        menu.start(user);
        stage.show();

    }
}
