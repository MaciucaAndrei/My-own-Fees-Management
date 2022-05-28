package project.licenta;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.deltaspike.core.util.StringUtils;
import project.licenta.entity.Reminder;
import project.licenta.entity.Taxes;
import project.licenta.service.ReminderService;
import project.licenta.service.TaxesService;
import project.licenta.utils.GetInstance;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class Costs {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnFood;

    @FXML
    private Button btnFree;

    @FXML
    private Button btnGraphic;

    @FXML
    private Button btnLibrary;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnNewReminder;

    @FXML
    private Button btnNewRentFee;

    @FXML
    private Button btnNewTax;

    @FXML
    private Button btnReminder;

    @FXML
    private Button btnRent;

    @FXML
    private Button btnTransport;

    @FXML
    private ComboBox<String> cmbDays;

    @FXML
    private ComboBox<String> cmbReminderType;

    @FXML
    private ComboBox<String> cmbRentType;

    @FXML
    private ComboBox<String> cmbUtilities;

    @FXML
    private DatePicker dtpReminder;

    @FXML
    private DatePicker dtpRent;

    @FXML
    private DatePicker dtpTax;

    @FXML
    private Label lblErrorReminder;

    @FXML
    private Label lblMenu;

    @FXML
    private Label lblMenuClose;

    @FXML
    private Label lblRentError;

    @FXML
    private Label lblTaxError;

    @FXML
    private AnchorPane paneMenu;

    @FXML
    private AnchorPane paneReminder;

    @FXML
    private AnchorPane paneRent;

    @FXML
    private AnchorPane paneSlider;

    @FXML
    private AnchorPane paneTax;

    @FXML
    private AnchorPane paneUtilities;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtTaxAmount;

    @FXML
    private TextField txtTaxType;

    private String user;
    private ReminderService reminderService = GetInstance.of(ReminderService.class);
    private TaxesService taxesService = GetInstance.of(TaxesService.class);


    public void btnBackOnClick(ActionEvent event) throws IOException, AWTException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("menu.fxml"));
        Stage stage =(Stage) btnBack.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        Menu menu = loader.getController();
        menu.start(user);
        stage.show();
    }


     public void btnLibraryOnClick(ActionEvent event) throws IOException {
         FXMLLoader loader= new FXMLLoader(getClass().getResource("library.fxml"));
         Stage stage =(Stage) btnBack.getScene().getWindow();
         stage.setScene(new Scene(loader.load()));
         Library menu = loader.getController();
         menu.start(user);
         stage.show();
    }


    public void btnLogoutOnClick(ActionEvent event) throws IOException {
        Stage logout= (Stage) btnLogout.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login.fxml"));
        Scene scene= new Scene(fxmlLoader.load());
        logout.setScene(scene);
    }


    public void start(String user)
    {
        this.user= user;
        paneSlider.setTranslateX(-216);
        lblMenu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(paneSlider);

            slide.setToX(0);
            slide.play();

            paneSlider.setTranslateX(-216);

            slide.setOnFinished((ActionEvent e)-> {
                lblMenu.setVisible(false);
                lblMenuClose.setVisible(true);
            });
        });

        lblMenuClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(paneSlider);

            slide.setToX(-216);
            slide.play();

            paneSlider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)-> {
                lblMenu.setVisible(true);
                lblMenuClose.setVisible(false);
            });
        });

    }

    public void btnRentOnClick(ActionEvent event)
    {
        paneRent.setVisible(true);
        cmbRentType.getItems().addAll("Rent","Student residence");
        LocalDate l = LocalDate.now();
        dtpRent.setValue(l);
        List<Taxes> all = taxesService.findAll();
        if(all!=null) {
            for (Taxes tax : all) {
                if (tax.getUser().equals(user) && tax.getPayment_type().contains("Rent")) {
                    cmbRentType.setValue("Rent");
                    cmbRentTypeOnChange(event);

                } else if (tax.getUser().equals(user) && tax.getPayment_type().contains("Student residence")) {
                    cmbRentType.setValue("Student residence");
                }

            }
        }


    }
    public void cmbRentTypeOnChange(ActionEvent event)
    {
        if(StringUtils.isNotEmpty(cmbRentType.getValue())) {
            if (cmbRentType.getValue().equals("Rent")) {
                paneUtilities.setVisible(true);
                if(cmbUtilities.getItems().isEmpty()) {
                    cmbUtilities.getItems().addAll("Rent fee", "Maintenance bill", "Current bill", "Water bill", "Gas bill", "cable+internet bill");
                }
                }else if(cmbRentType.getValue().equals("Student residence"))
            {
                paneUtilities.setVisible(false);
            }
        }

    }

    public boolean rentFieldsValidation(String type,String text, Calendar date, String amount)
    {
        Calendar c = Calendar.getInstance();

        if((type.isBlank() || amount.isBlank()))
        {
            lblRentError.setText("Fill in all  the fields");
            Paint paint = Paint.valueOf("red");
            lblRentError.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
            lblRentError.setFont(font);
            return false;
        }
        if(date.getTimeInMillis()> c.getTimeInMillis())
        {
            lblRentError.setText("The payment date cannot be in the future");
            javafx.scene.paint.Paint paint = Paint.valueOf("red");
            lblRentError.setTextFill(paint);
            javafx.scene.text.Font font = new Font("Gadugi", 10);
            lblRentError.setFont(font);
            return false;
        }
        if(amount.matches("[a-zA-Z]+"))
        {
            lblRentError.setText("Please enter just  numbers without letters");
            Paint paint = Paint.valueOf("red");
            lblRentError.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
            lblRentError.setFont(font);
            return false;
        }
        List<Taxes>all = taxesService.findAll();
        for(Taxes t : all)
        {
            if(t.getUser().equals(user) && t.getPayment_type().equals(text) && date.get(Calendar.MONTH)==t.getPayment_date().get(Calendar.MONTH)
            && date.get(Calendar.YEAR)==t.getPayment_date().get(Calendar.YEAR) && t.getPayment_amount()==Double.parseDouble(amount)) {
                lblRentError.setText(text+"has been already added");
                Paint paint = Paint.valueOf("red");
                lblRentError.setTextFill(paint);
                Font font = new Font("Gadugi", 10);
                lblRentError.setFont(font);
                return false;
            }
        }
        return true;
    }

    public void btnNewRentFeeOnClick(ActionEvent event)
    {
        Calendar c= Calendar.getInstance();
        String text="";
        if(cmbRentType.getValue().equals("Rent"))
        {
            text=cmbUtilities.getValue();
        }else
        {
            text="Student residence fee";
        }
        c.set(dtpRent.getValue().getYear(),dtpRent.getValue().getMonthValue()-1,dtpRent.getValue().getDayOfMonth());
        if(rentFieldsValidation(cmbRentType.getValue(),text,c,txtAmount.getText()))
        {
            Taxes taxes = new Taxes(user,text,c,Double.parseDouble(txtAmount.getText()));
            Taxes save = taxesService.save(taxes);
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText( text +" has been successfully registered");
            a.show();
            paneRent.setVisible(false);
            cmbRentType.getItems().clear();
            cmbUtilities.getItems().clear();
            lblRentError.setText("");
            txtAmount.clear();
            dtpRent.getEditor().clear();
        }
    }


}
