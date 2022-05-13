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
import project.licenta.entity.Taxes;
import project.licenta.service.TaxesService;
import project.licenta.utils.GetInstance;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;


public class Semesterfee {

    @FXML
    private Label lblSemester;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnPaid;
    @FXML
    private DatePicker dtpPaid;
    @FXML
    private TextField txtAmount;
    @FXML
    private Button btnNewTaxes;
    @FXML
    private AnchorPane panePaid;
    @FXML
    private Label lblError;

    private String user;
    private String semester_text;
    private Semester semester;
    private TaxesService taxesService= GetInstance.of(TaxesService.class);


    public void start(String user, String semester_text, Semester semester)
    {
        this.user=user;
        this.semester_text=semester_text;
        this.semester=semester;
        lblSemester.setText(semester_text);
    }

    public void btnPaidOnClick(ActionEvent e) {
        Calendar c= Calendar.getInstance();
        LocalDate date= LocalDate.of(c.get(Calendar.YEAR),c.get(Calendar.MONTH)+1,c.get(Calendar.DAY_OF_MONTH));
        dtpPaid.setValue(date);
        panePaid.setVisible(true);
    }

    public boolean fieldsValidation(String amount, Calendar date)
    {
        Calendar c = Calendar.getInstance();
        String university_year = semester.getUniversity_year();
        String[] years=university_year.split("-");
        String taxe_type= "Semester fee for "+semester_text;
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

        if(txtAmount.getText()==null || txtAmount.getText().isBlank())
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
        List<Taxes>all = taxesService.findAll();
       for(Taxes t : all)
        {
            if(t.getUser().equals(user) && t.getPayment_type().equals(taxe_type) && t.getPayment_amount()==Double.parseDouble(amount)) {
                lblError.setText("The semester fee was been already added");
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
        if(fieldsValidation(txtAmount.getText(),c))
        {
            String taxes_type="Semester fee for "+semester_text;
            Taxes taxes = new Taxes(user,taxes_type,c,Double.parseDouble(txtAmount.getText()));
            Taxes save = taxesService.save(taxes);
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("The semester fee has been successfully registered");
            a.show();
            panePaid.setVisible(false);
            lblError.setText("");
            txtAmount.clear();
            dtpPaid.getEditor().clear();
        }
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

    public void btnLogoutOnClick(ActionEvent event) throws IOException
    {
        Stage login= (Stage) btnLogout.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login.fxml"));
        Scene scene= new Scene(fxmlLoader.load());
        login.setScene(scene);
    }


}
