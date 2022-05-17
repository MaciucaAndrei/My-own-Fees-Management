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
import project.licenta.entity.Subjects;
import project.licenta.entity.Taxes;
import project.licenta.service.SubjectsService;
import project.licenta.service.TaxesService;
import project.licenta.utils.GetInstance;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Course_view {
    @FXML
    private Label lblSemester;
    @FXML
    private Label lblCourse;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnLogout;
    @FXML
    private Label lblError;
    @FXML
    private AnchorPane panePaid;
    @FXML
    private DatePicker dtpPaid;
    @FXML
    private ComboBox<String> cmbTaxType;
    @FXML
    private TextField txtAmount;
    private String user;
    private Semester semester;
    private SubjectsService subjectsService = GetInstance.of(SubjectsService.class);
    private TaxesService taxesService= GetInstance.of(TaxesService.class);


    public void start(Subjects subject, Semester semester)
    {
        this.user = subject.getUser();
        this.semester=semester;
        lblSemester.setText(subject.getSemester());
        lblCourse.setText(subject.getSubject_name());

    }
    public void btnPaidOnClick(ActionEvent event)
    {
        Calendar c= Calendar.getInstance();
        LocalDate date= LocalDate.of(c.get(Calendar.YEAR),c.get(Calendar.MONTH)+1,c.get(Calendar.DAY_OF_MONTH));
        dtpPaid.setValue(date);
        panePaid.setVisible(true);
        cmbTaxType.getItems().add("The fee for the third presentation");
        cmbTaxType.getItems().add("Subject fee");
    }
    public boolean fieldsValidation(String amount, Calendar date,String tax)
    {
        Calendar c = Calendar.getInstance();
        String university_year = semester.getUniversity_year();
        String[] years=university_year.split("-");
        if(date.getTimeInMillis()> c.getTimeInMillis())
        {
            lblError.setText("The payment date cannot be in the future");
            Paint paint = Paint.valueOf("red");
            lblError.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
            lblError.setFont(font);
            return false;
        }
        if(date.get(Calendar.YEAR)<Integer.parseInt(years[0]))
        {
            lblError.setText("The payment date must be in the semester's university year");
            Paint paint = Paint.valueOf("red");
            lblError.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
            lblError.setFont(font);
            return false;
        }

        if(txtAmount.getText().isBlank())
        {
            lblError.setText("Fill in  the field");
            Paint paint = Paint.valueOf("red");
            lblError.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
            lblError.setFont(font);
            return false;
        }
        if(amount.matches("[a-zA-Z]+"))
        {
            lblError.setText("Please enter just  numbers without letters");
            Paint paint = Paint.valueOf("red");
            lblError.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
            lblError.setFont(font);
            return false;
        }
        if(tax==null)
        {
            lblError.setText("Please select a tax type from the specific list");
            Paint paint = Paint.valueOf("red");
            lblError.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
            lblError.setFont(font);
            return false;
        }
        List<Taxes>all = taxesService.findAll();
        String taxes_type= tax+" for "+lblCourse.getText();
        for(Taxes t : all)
        {
            if(t.getUser().equals(user) && t.getPayment_type().equals(taxes_type) && t.getPayment_amount()==Double.parseDouble(amount)) {
                lblError.setText("The tax was been already added");
                Paint paint = Paint.valueOf("red");
                lblError.setTextFill(paint);
                Font font = new Font("Gadugi", 10);
                lblError.setFont(font);
                return false;
            }
        }
        return true;
    }

    public void btnNewTaxesOnClick(ActionEvent e)
    {
        Calendar c= Calendar.getInstance();
        c.set(dtpPaid.getValue().getYear(), dtpPaid.getValue().getMonthValue()-1,dtpPaid.getValue().getDayOfMonth());
        if(fieldsValidation(txtAmount.getText(),c,cmbTaxType.getValue()))
        {
            String taxes_type= cmbTaxType.getValue()+" for "+lblCourse.getText();
            Taxes taxes = new Taxes(user,taxes_type,c,Double.parseDouble(txtAmount.getText()));
            Taxes save = taxesService.save(taxes);
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText(taxes_type+" has been successfully registered");
            a.show();
            panePaid.setVisible(false);
            lblError.setText("");
            txtAmount.clear();
            dtpPaid.getEditor().clear();
            cmbTaxType.getItems().clear();
        }
    }

    public void btnBackOnClick(ActionEvent event) throws IOException
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("subjects_view.fxml"));
        Stage stage =(Stage) btnBack.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
       Subjects_view menu = loader.getController();
        menu.start(user,lblSemester.getText(),semester);
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
