package project.licenta;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
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
import project.licenta.entity.Subjects;
import project.licenta.entity.Taxes;
import project.licenta.service.ReminderService;
import project.licenta.service.TaxesService;
import project.licenta.utils.GetInstance;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
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
    private Button btnPaid;

    @FXML
    private Button btnReminder;

    @FXML
    private Label lblError;
    @FXML
    private Label lblErrorReminder;
    @FXML
    private AnchorPane panePaid;
    @FXML
    private AnchorPane paneReminder;
    @FXML
    private DatePicker dtpPaid;
    @FXML
    private DatePicker dtpReminder;
    @FXML
    private ComboBox<String> cmbTaxType;
    @FXML
    private ComboBox<String> cmbDays;
    @FXML
    private ComboBox<String> cmbReminderType;
    @FXML
    private TextField txtAmount;
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
    private Semester semester;
    private final TaxesService taxesService = GetInstance.of(TaxesService.class);
    private final ReminderService reminderService = GetInstance.of(ReminderService.class);


    public void start(Subjects subject, Semester semester) {
        this.user = subject.getUser();
        this.semester = semester;
        lblSemester.setText(subject.getSemester());
        lblCourse.setText(subject.getSubject_name().toUpperCase());
        paneSlider.setTranslateX(-250);
        lblMenu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(paneSlider);

            slide.setToX(0);
            slide.play();

            paneSlider.setTranslateX(-250);

            slide.setOnFinished((ActionEvent e) -> {
                lblMenu.setVisible(false);
                lblMenuClose.setVisible(true);
            });
        });

        lblMenuClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(paneSlider);

            slide.setToX(-250);
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

    public void btnPaidOnClick() {
        Calendar c = Calendar.getInstance();
        LocalDate date = LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
        dtpPaid.setValue(date);
        panePaid.setVisible(true);
        cmbTaxType.getItems().add("The fee for the third presentation");
        cmbTaxType.getItems().add("Subject fee");
    }

    public void btnReminderOnClick() {
        Calendar c = Calendar.getInstance();
        LocalDate date = LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
        dtpReminder.setValue(date);
        paneReminder.setVisible(true);
        cmbDays.getItems().add("Day");
        cmbDays.getItems().add("Week");
        cmbDays.getItems().add("Month");
        cmbReminderType.getItems().add("The fee for the third presentation");
        cmbReminderType.getItems().add("Subject fee");
        cmbReminderType.getItems().add("A project");
        cmbReminderType.getItems().add("A test");
        cmbReminderType.getItems().add("Exam");
    }

    public boolean paidFieldsValidation(String amount, Calendar date, String tax) {
        Calendar c = Calendar.getInstance();
        String university_year = semester.getUniversity_year();
        String[] years = university_year.split("-");
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

        if (txtAmount.getText().isBlank()) {
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
        if (tax == null) {
            lblError.setText("Please select a tax type from the specific list");
            Paint paint = Paint.valueOf("red");
            lblError.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
            lblError.setFont(font);
            return false;
        }
        List<Taxes> all = taxesService.findAll();
        String taxes_type = tax + " for " + lblCourse.getText();
        for (Taxes t : all) {
            if (t.getUser().equals(user) && t.getPayment_type().equals(taxes_type)) {
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

    public boolean reminderFieldsValidation(String days, Calendar date, String reminder) {
        Calendar c = Calendar.getInstance();
        String university_year = semester.getUniversity_year();
        String[] years = university_year.split("-");
        if (date.getTimeInMillis() < c.getTimeInMillis()) {
            lblErrorReminder.setText("The reminder cannot be from the past");
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

        if (days == null || reminder == null) {
            lblErrorReminder.setText("Fill in  the field");
            Paint paint = Paint.valueOf("red");
            lblErrorReminder.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
            lblErrorReminder.setFont(font);
            return false;
        }
        List<Reminder> all = reminderService.findAll();
        String message = lblCourse.getText() + ": " + reminder;
        for (Reminder t : all) {
            if (t.getUsername().equals(user) && t.getMessage().equals(message) && t.getDays().equals(days)
                    && t.getDeadline().get(Calendar.YEAR) == date.get(Calendar.YEAR) && t.getDeadline().get(Calendar.MONTH) == date.get(Calendar.MONTH)
                    && t.getDeadline().get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)) {
                lblErrorReminder.setText("The reminder was been already added");
                Paint paint = Paint.valueOf("red");
                lblErrorReminder.setTextFill(paint);
                Font font = new Font("Gadugi", 10);
                lblErrorReminder.setFont(font);
                return false;
            }
        }
        return true;
    }

    public void btnNewTaxesOnClick() {
        Calendar c = Calendar.getInstance();
        c.set(dtpPaid.getValue().getYear(), dtpPaid.getValue().getMonthValue() - 1, dtpPaid.getValue().getDayOfMonth());
        if (paidFieldsValidation(txtAmount.getText(), c, cmbTaxType.getValue())) {
            String taxes_type = cmbTaxType.getValue() + " for " + lblCourse.getText();
            Taxes taxes = new Taxes(user, taxes_type, c, Double.parseDouble(txtAmount.getText()));
            Taxes save = taxesService.save(taxes);
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText(taxes_type + " has been successfully registered");
            a.show();
            panePaid.setVisible(false);
            lblError.setText("");
            txtAmount.clear();
            dtpPaid.getEditor().clear();
            cmbTaxType.getItems().clear();
        }
    }

    public void btnNewReminderOnClick() {
        Calendar c = Calendar.getInstance();
        c.set(dtpReminder.getValue().getYear(), dtpReminder.getValue().getMonthValue() - 1, dtpReminder.getValue().getDayOfMonth());
        String title;
        if (reminderFieldsValidation(cmbDays.getValue(), c, cmbReminderType.getValue())) {
            switch (cmbReminderType.getValue()) {
                case "The fee for the third presentation":
                case "Subject fee": {
                    title = "Reminder you need to pay";
                    break;
                }
                default: {
                    title = "Reminder you need to study ";
                    break;
                }
            }
            String message = lblCourse.getText() + ": " + cmbReminderType.getValue();
            Reminder reminder = new Reminder(user, title, message, c, cmbDays.getValue());
            Reminder save = reminderService.save(reminder);
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("The reminder has been successfully registered");
            a.show();
            paneReminder.setVisible(false);
            lblErrorReminder.setText("");
            cmbDays.getItems().clear();
            dtpPaid.getEditor().clear();
            cmbReminderType.getItems().clear();
        }
    }


    public void btnBackOnClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("subjects_view.fxml"));
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        Subjects_view menu = loader.getController();
        menu.start(user, lblSemester.getText(), semester);
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

}
