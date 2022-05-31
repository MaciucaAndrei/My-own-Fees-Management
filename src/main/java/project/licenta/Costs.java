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
    private ComboBox<String> cmbTax;

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


    public void btnGraphicOnClick(ActionEvent event) throws IOException
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("graphic.fxml"));
        Stage stage =(Stage) btnGraphic.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        Graphic menu = loader.getController();
        menu.start(user);
        stage.show();
    }

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
        paneSlider.setTranslateX(-222);
        lblMenu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(paneSlider);

            slide.setToX(0);
            slide.play();

            paneSlider.setTranslateX(-222);

            slide.setOnFinished((ActionEvent e)-> {
                lblMenu.setVisible(false);
                lblMenuClose.setVisible(true);
            });
        });

        lblMenuClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(paneSlider);

            slide.setToX(-222);
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
    public void btnReminderOnClick(ActionEvent event)
    {
        LocalDate l = LocalDate.now();
        dtpReminder.setValue(l);
        cmbDays.getItems().addAll("Day","Week","Month");
        paneReminder.setVisible(true);
        List<Taxes> all = taxesService.findAll();
        int flag=0;
        for(Taxes tax : all)
        {
            if(tax.getUser().equals(user) && tax.getPayment_type().contains("Rent"))
            {
                flag=1;
                cmbReminderType.getItems().addAll("Rent fee", "Maintenance bill", "Current bill", "Water bill", "Gas bill", "cable+internet bill");
                break;
            }else if(tax.getUser().equals(user) && tax.getPayment_type().contains("Student residence"))
            {
                flag=1;
                cmbReminderType.getItems().add("Student residence fee");
                break;
            }
        }
        if(flag==0)
        {
            cmbReminderType.getItems().addAll("Student residence fee","Rent fee", "Maintenance bill", "Current bill", "Water bill", "Gas bill", "cable+internet bill");

        }
    }

    public void btnTransportOnClick(ActionEvent event)
    {
        paneTax.setVisible(true);
        txtTaxType.setText("Transport fee");
        if(cmbTax.getItems().isEmpty())
        {
            cmbTax.getItems().addAll("Today","This week","This month");
        }
    }

    public void btnFoodOnClick(ActionEvent event)
    {
        paneTax.setVisible(true);
        txtTaxType.setText("Food tax");
        if(cmbTax.getItems().isEmpty())
        {
            cmbTax.getItems().addAll("Today","This week","This month");
        }
    }

    public void btnFreeOnCLick(ActionEvent event)
    {
        paneTax.setVisible(true);
        txtTaxType.setText("Leisure fee");
        if(cmbTax.getItems().isEmpty())
        {
            cmbTax.getItems().addAll("Today","This week","This month");
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

    public boolean reminderFieldsValidation(String type,String days, Calendar date) {
        Calendar c = Calendar.getInstance();
        if (date.getTimeInMillis() < c.getTimeInMillis()) {
            lblErrorReminder.setText("The payment deadline cannot be from the past");
            Paint paint = Paint.valueOf("red");
            lblErrorReminder.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
            lblErrorReminder.setFont(font);
            return false;
        }
        if (days.isBlank() || type.isBlank()) {
            lblErrorReminder.setText("Fill in  the field");
            Paint paint = Paint.valueOf("red");
            lblErrorReminder.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
            lblErrorReminder.setFont(font);
            return false;
        }
        List<Reminder> all = reminderService.findAll();
        for (Reminder reminder : all)
        {
            if(reminder.getUsername().equals(user) && reminder.getMessage().equals(type) && reminder.getDays().equals(days)
                    && reminder.getDeadline().get(Calendar.YEAR)==date.get(Calendar.YEAR) && reminder.getDeadline().get(Calendar.MONTH)==date.get(Calendar.MONTH)
                    && reminder.getDeadline().get(Calendar.DAY_OF_MONTH)==date.get(Calendar.DAY_OF_MONTH)) {
                lblErrorReminder.setText("The reminder has been already added");
                Paint paint = Paint.valueOf("red");
                lblErrorReminder.setTextFill(paint);
                Font font = new Font("Gadugi", 10);
                lblErrorReminder.setFont(font);
                return false;
            }

        }
            return true;
    }

    public boolean taxFieldsValidation(String type,String date,String amount)
    {
        if((date.isBlank() || amount.isBlank()))
        {
            lblTaxError.setText("Fill in all  the fields");
            Paint paint = Paint.valueOf("red");
            lblTaxError.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
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
        List<Taxes> all = taxesService.findAll();
        Calendar c = Calendar.getInstance();
                for(Taxes tax : all)
                {
                     if(tax.getUser().equals(user) && tax.getPayment_type().equals(type) && c.get(Calendar.MONTH)==tax.getPayment_date().get(Calendar.MONTH)
                        && c.get(Calendar.YEAR)==tax.getPayment_date().get(Calendar.YEAR) && tax.getPayment_amount()==Double.parseDouble(amount)) {
                         lblTaxError.setText(type+"has been already added");
                         Paint paint = Paint.valueOf("red");
                         lblTaxError.setTextFill(paint);
                         Font font = new Font("Gadugi", 10);
                         lblTaxError.setFont(font);
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

    public void btnNewReminderOnClick(ActionEvent event)
    {
        Calendar c= Calendar.getInstance();
        c.set(dtpReminder.getValue().getYear(), dtpReminder.getValue().getMonthValue()-1,dtpReminder.getValue().getDayOfMonth());
        if(reminderFieldsValidation(cmbReminderType.getValue(),cmbDays.getValue(),c))
        {
            String title ="Reminder you need to pay:";
            Reminder reminder = new Reminder(user,title,cmbReminderType.getValue(),c,cmbDays.getValue());
            Reminder save= reminderService.save(reminder);
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("The reminder has been successfully registered");
            a.show();
            paneReminder.setVisible(false);
            lblErrorReminder.setText("");
            cmbDays.getItems().clear();
            dtpReminder.getEditor().clear();
            cmbReminderType.getItems().clear();
        }
    }

    public void btnNewTaxOnClick(ActionEvent event)
    {
        if(taxFieldsValidation(txtTaxType.getText(),cmbTax.getValue(),txtTaxAmount.getText()))
        {
            switch(cmbTax.getValue())
            {
                case "Today":
                {
                    Calendar c = Calendar.getInstance();
                    Taxes taxes = new Taxes(user,txtTaxType.getText(),c,Double.parseDouble(txtTaxAmount.getText()));
                    Taxes save = taxesService.save(taxes);
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setHeaderText( txtTaxType.getText() +" has been successfully registered");
                    a.show();
                    paneRent.setVisible(false);
                    cmbRentType.getItems().clear();
                    cmbUtilities.getItems().clear();
                    lblRentError.setText("");
                    txtAmount.clear();
                    dtpRent.getEditor().clear();
                    break;
                }
                case "This week":
                {
                    Calendar c = Calendar.getInstance();
                    c.setWeekDate(c.getWeekYear(),c.get(Calendar.WEEK_OF_YEAR),Calendar.MONDAY);
                    for(int i=0;i<7;i++)
                    {
                        c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)+1);
                        Taxes taxes = new Taxes(user,txtTaxType.getText(),c,Double.parseDouble(txtTaxAmount.getText())/7);
                        Taxes save = taxesService.save(taxes);
                    }
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setHeaderText( txtTaxType.getText() +" has been successfully registered");
                    a.show();
                    paneRent.setVisible(false);
                    cmbRentType.getItems().clear();
                    cmbUtilities.getItems().clear();
                    lblRentError.setText("");
                    txtAmount.clear();
                    dtpRent.getEditor().clear();
                    break;
                }
                case "This month":
                {
                    Calendar c = Calendar.getInstance();
                    int month = c.get(Calendar.MONTH);
                    int numberOfDays=0;
                    while(c.get(Calendar.MONTH)==month)
                    {
                        numberOfDays++;
                        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), numberOfDays);
                    }
                    numberOfDays--;
                    c.set(c.get(Calendar.YEAR),month,1);
                  for(int i=1;i<=numberOfDays;i++)
                  {
                      c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH),i);
                      Taxes taxes = new Taxes(user, txtTaxType.getText(), c, Double.parseDouble(txtTaxAmount.getText()) / numberOfDays);
                      Taxes save = taxesService.save(taxes);
                  }
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setHeaderText( txtTaxType.getText() +" has been successfully registered");
                    a.show();
                    paneTax.setVisible(false);
                    txtTaxType.clear();
                    lblTaxError.setText("");
                    txtTaxAmount.clear();
                    cmbTax.getItems().clear();
                    break;


                }
            }
        }
    }
}
