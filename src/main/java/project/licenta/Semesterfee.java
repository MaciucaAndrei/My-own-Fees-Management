package project.licenta;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.io.FileUtils;
import project.licenta.entity.Reminder;
import project.licenta.entity.Semester;
import project.licenta.entity.Taxes;
import project.licenta.service.ReminderService;
import project.licenta.service.TaxesService;
import project.licenta.utils.GetInstance;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
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
    private Button btnReminder;
    @FXML
    private DatePicker dtpPaid;
    @FXML
    private DatePicker dtpReminder;
    @FXML
    private TextField txtAmount;
    @FXML
    private ComboBox<String> cmbDays;
    @FXML
    private Button btnNewTaxes;
    @FXML
    private Button btnNewReminder;
    @FXML
    private AnchorPane panePaid;
    @FXML
    private AnchorPane paneReminder;
    @FXML
    private Label lblError;
    @FXML
    private Label lblErrorReminder;
    @FXML
    private AnchorPane paneSlider;
    @FXML
    private AnchorPane paneMenu;
    @FXML
    private Label lblMenu;
    @FXML
    private Label lblMenuClose;
    @FXML
    private ImageView image1;
    @FXML
    private ImageView image2;

    private String user;
    private String semester_text;
    private Semester semester;
    private final TaxesService taxesService = GetInstance.of(TaxesService.class);
    private final ReminderService reminderService = GetInstance.of(ReminderService.class);


    public void start(String user, String semester_text, Semester semester) {
        this.user = user;
        this.semester_text = semester_text;
        this.semester = semester;
        lblSemester.setText(semester_text);
        paneSlider.setTranslateX(-240);
        lblMenu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(paneSlider);

            slide.setToX(0);
            slide.play();

            slide.setOnFinished((ActionEvent e) -> {
                lblMenu.setVisible(false);
                lblMenuClose.setVisible(true);
            });
        });

        lblMenuClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(paneSlider);

            slide.setToX(-240);
            slide.play();

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
        btnPaid.setOnMouseEntered(e -> {
            btnPaid.setStyle("-fx-background-color: transparent;-fx-text-fill:#66b3ff; -fx-border-width: 3px;" +
                    "-fx-border-color: #66b3ff;");
            btnPaid.setTranslateX(5);
            btnPaid.setTranslateY(-5);
            javafx.scene.image.Image image = null;
            try {
                image = new javafx.scene.image.Image(Library.class.getResource("images/money_blue.png").openStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            image1.setImage(image);
        });
        btnPaid.setOnMouseEntered(e -> {
            btnPaid.setStyle("-fx-background-color: transparent;-fx-text-fill:#66b3ff; -fx-border-width: 3px;" +
                    "-fx-border-color: #66b3ff;");
            btnPaid.setTranslateX(5);
            btnPaid.setTranslateY(-5);
            javafx.scene.image.Image image = null;
            try {
                image = new javafx.scene.image.Image(Library.class.getResource("images/money_blue.png").openStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            image1.setImage(image);
        });
        btnPaid.setOnMouseExited(e -> {
            btnPaid.setStyle("-fx-background-color: transparent;-fx-text-fill: #d9e9f2; -fx-border-width: 3px;" +
                    "-fx-border-color:#d9e9f2;");
            btnPaid.setTranslateX(-5);
            btnPaid.setTranslateY(5);
            javafx.scene.image.Image image = null;
            try {
                image = new javafx.scene.image.Image(Library.class.getResource("images/money_white.png").openStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            image1.setImage(image);
        });
        btnReminder.setOnMouseEntered(e -> {
            btnReminder.setStyle("-fx-background-color: transparent;-fx-text-fill:#66b3ff; -fx-border-width: 3px;" +
                    "-fx-border-color: #66b3ff;");
            btnReminder.setTranslateX(5);
            btnReminder.setTranslateY(-5);
            javafx.scene.image.Image image = null;
            try {
                image = new javafx.scene.image.Image(Library.class.getResource("images/reminder_blue.png").openStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            image2.setImage(image);
        });
        btnReminder.setOnMouseExited(e -> {
            btnReminder.setStyle("-fx-background-color: transparent;-fx-text-fill: #d9e9f2; -fx-border-width: 3px;" +
                    "-fx-border-color:#d9e9f2;");
            btnReminder.setTranslateX(-5);
            btnReminder.setTranslateY(5);
            javafx.scene.image.Image image = null;
            try {
                image = new javafx.scene.image.Image(Library.class.getResource("images/reminder_white.png").openStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            image2.setImage(image);
        });
    }

    public void btnPaidOnClick(ActionEvent e) {
        Calendar c = Calendar.getInstance();
        LocalDate date = LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
        dtpPaid.setValue(date);
        panePaid.setVisible(true);
    }

    public boolean paidFieldsValidation(String amount, Calendar date) {
        Calendar c = Calendar.getInstance();
        String university_year = semester.getUniversity_year();
        String[] years = university_year.split("-");
        String tax_type = "Semester fee for " + semester_text;
        if (date.getTimeInMillis() > c.getTimeInMillis()) {
            lblError.setText("The payment date cannot be in the future");
            Paint paint = Paint.valueOf("red");
            lblError.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
            lblError.setFont(font);
            return false;
        }
        if (date.get(Calendar.YEAR) < Integer.parseInt(years[0])) {
            lblError.setText("The payment date must be in the semester's university year");
            Paint paint = Paint.valueOf("red");
            lblError.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
            lblError.setFont(font);
            return false;
        }

        if (amount.isBlank()) {
            lblError.setText("Fill in  the field");
            Paint paint = Paint.valueOf("red");
            lblError.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
            lblError.setFont(font);
            return false;
        }
        if (amount.matches("[a-zA-Z]+")) {
            lblError.setText("Please enter just  numbers without letters");
            Paint paint = Paint.valueOf("red");
            lblError.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
            lblError.setFont(font);
            return false;
        }
        List<Taxes> all = taxesService.findAll();
        for (Taxes t : all) {
            if (t.getUser().equals(user) && t.getPayment_type().equals(tax_type)) {
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

    public boolean reminderFieldsValidation(String days, Calendar date) {
        Calendar c = Calendar.getInstance();
        String[] years = semester.getUniversity_year().split("-");
        if (date.getTimeInMillis() < c.getTimeInMillis()) {
            lblErrorReminder.setText("The payment deadline cannot be from the past");
            Paint paint = Paint.valueOf("red");
            lblErrorReminder.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
            lblErrorReminder.setFont(font);
            return false;
        }
        if (date.get(Calendar.YEAR) > Integer.parseInt(years[1])) {
            lblErrorReminder.setText("The reminder date must be in the semester's university year");
            Paint paint = Paint.valueOf("red");
            lblErrorReminder.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
            lblErrorReminder.setFont(font);
            return false;
        }

        if (days == null) {
            lblErrorReminder.setText("Fill in  the field");
            Paint paint = Paint.valueOf("red");
            lblErrorReminder.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
            lblErrorReminder.setFont(font);
            return false;
        }
        List<Reminder> all = reminderService.findAll();
        String message = "Semester fee for " + semester_text;
        for (Reminder reminder : all) {
            if (reminder.getUsername().equals(user) && reminder.getMessage().equals(message) && reminder.getDays().equals(days)
                    && reminder.getDeadline().get(Calendar.YEAR) == date.get(Calendar.YEAR) && reminder.getDeadline().get(Calendar.MONTH) == date.get(Calendar.MONTH)
                    && reminder.getDeadline().get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)) {
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

    public void btnNewTaxesOnClick(ActionEvent e) {
        Calendar c = Calendar.getInstance();
        c.set(dtpPaid.getValue().getYear(), dtpPaid.getValue().getMonthValue() - 1, dtpPaid.getValue().getDayOfMonth());
        if (paidFieldsValidation(txtAmount.getText(), c)) {
            String taxes_type = "Semester fee for " + semester_text;
            Taxes taxes = new Taxes(user, taxes_type, c, Double.parseDouble(txtAmount.getText()));
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

    public void btnBackOnClick(ActionEvent event) throws IOException, AWTException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("subject_view.fxml"));
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
       Subjects_view menu = loader.getController();
        menu.start(user,lblSemester.getText(),semester);
        stage.show();
    }

    public void btnLogoutOnClick(ActionEvent event) throws IOException {
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

    public void btnReminderOnClick(ActionEvent event) {
        Calendar c = Calendar.getInstance();
        LocalDate date = LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
        dtpReminder.setValue(date);
        paneReminder.setVisible(true);
        cmbDays.getItems().add("Day");
        cmbDays.getItems().add("Week");
        cmbDays.getItems().add("Month");
    }

    public void btnNewReminderOnClick(ActionEvent event) {
        Calendar c = Calendar.getInstance();
        c.set(dtpReminder.getValue().getYear(), dtpReminder.getValue().getMonthValue() - 1, dtpReminder.getValue().getDayOfMonth());
        if (reminderFieldsValidation(cmbDays.getValue(), c)) {
            String title = "Reminder you need to pay:";
            String message = "Semester fee for " + semester_text;
            Reminder reminder = new Reminder(user, title, message, c, cmbDays.getValue());
            Reminder save = reminderService.save(reminder);
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("The reminder has been successfully registered");
            a.show();
            paneReminder.setVisible(false);
            lblErrorReminder.setText("");
            cmbDays.getItems().clear();
            dtpPaid.getEditor().clear();
        }
    }

}
