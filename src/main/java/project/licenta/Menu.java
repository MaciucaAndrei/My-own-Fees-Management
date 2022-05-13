package project.licenta;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import project.licenta.entity.Semester;
import project.licenta.service.SemesterService;
import project.licenta.utils.GetInstance;

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
    private Button btnAddSemester;

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
    private Label label;

    private SemesterService semesterService= GetInstance.of(SemesterService .class);


    public String buttonText(Semester semester)
    {
        String text="";
        if(semester.getUniversity().length()<5)
        {
            text=semester.getUniversity()+" ";
        }else
        {
            String[] shortcuts=semester.getUniversity().split(" ");
            if(shortcuts.length==1)
            {
                text=shortcuts[0].substring(0,4);
            }else {
                for (String shortcut : shortcuts) {
                    if(shortcut.length()>3) {
                        text = text + shortcut.substring(0, 1);
                    }
                }
            }
            text=text+" ";

        }

        if(semester.getCollege().length()<5)
        {
            text=text+semester.getCollege()+" ";
        }else
        {
            String[] shortcuts=semester.getCollege().split(" ");
            if(shortcuts.length==1)
            {
                text=text+shortcuts[0].substring(0,4);
            }else {
                for (String shortcut : shortcuts) {
                    if(shortcut.length()>3) {
                        text = text + shortcut.substring(0, 1);
                    }
                }
            }
            text=text+" ";
        }

        if(semester.getDepartment().length()<5)
        {
            text=text+semester.getDepartment()+" ";
        }else
        {
            String[] shortcuts=semester.getDepartment().split(" ");
            if(shortcuts.length==1)
            {
                text=text+shortcuts[0].substring(0,4);
            }else {
                for (String shortcut : shortcuts) {
                    if(shortcut.length()>3) {
                        text = text + shortcut.substring(0, 1);
                    }
                }
            }
            text=text+" ";
        }
        text=text+"\n"+"An universitar: "+semester.getUniversity_year()+" An: "+semester.getYear()+" Semestru: "+semester.getSemester();
        return text;
    }

    public void showButtons(String user)
    {
        List<Semester> semesters = semesterService.findAll();
        double x=20;
        double y=50;
        for(Semester semester : semesters)
        {
            if(user.equals(semester.getUser().toString())) {
                Button button = new Button();
                button.setLayoutX(x);
                button.setLayoutY(y);
                button.setText(buttonText(semester));
                y = y + 45;
                Font font= new Font("Gadugi",15);
                button.setFont(font);
                Paint paint = Paint.valueOf("#d9e9f2");
                button.setTextFill(paint);
                button.setStyle("-fx-background-color: transparent");
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


    public void start(String user)
    {
        this.user= user;
        SpinnerValueFactory<Integer> yearFactory =new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4, 1);
        spnYear.setValueFactory(yearFactory);
        SpinnerValueFactory<Integer> semFactory =new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 2, 1);
        spnSem.setValueFactory(semFactory);
        showButtons(user);
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
           if(duplicate_shortcuts.equals(semester_shortcuts))
           {
               label.setText("This semester was already added");
               Paint paint = Paint.valueOf("red");
               label.setTextFill(paint);
               Font font = new Font("Gadugi",15);
               label.setFont(font);
               return false;
           }
           if(semester.getUniversity().toLowerCase().equals(duplicate.getUniversity().toLowerCase()) &&
                   semester.getCollege().toLowerCase().equals(duplicate.getCollege().toLowerCase()) &&
                   semester.getDepartment().toLowerCase().equals(duplicate.getDepartment().toLowerCase()) &&
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



    public void btnLogoutOnClick(ActionEvent event) throws IOException
    {
        Stage logout= (Stage) btnLogout.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login.fxml"));
        Scene scene= new Scene(fxmlLoader.load());
        logout.setScene(scene);
    }
    public void btnAddSemesterOnClick(ActionEvent event)
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
    public void btnAddOnClick(ActionEvent event)
    {
        if(FieldsValidation(txtUniv.getText().toString(),txtColl.getText().toString(),txtDep.getText().toString()
                ,txtUniv_year.getText().toString())&&Univ_yearValidation(txtUniv_year.getText().toString()))
        {
            Semester semester= new Semester(user,txtUniv.getText().toString(),txtColl.getText().toString()
                    ,txtDep.getText().toString(),txtUniv_year.getText().toString(),spnYear.getValue(),spnSem.getValue(),chbTaxes.isSelected());
            if(SemesterValidation(semester)) {
                Semester save = semesterService.save(semester);
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setHeaderText("The semester has been successfully added");
                a.show();
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

}
