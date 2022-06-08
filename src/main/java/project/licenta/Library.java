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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.io.FileUtils;
import project.licenta.entity.Books;
import project.licenta.entity.Reminder;
import project.licenta.service.BooksService;
import project.licenta.service.ReminderService;
import project.licenta.utils.GetInstance;
import project.licenta.utils.TableBooks;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class Library {

    @FXML
    private Button btnBack;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnReminder;
    @FXML
    private AnchorPane paneAdd;
    @FXML
    private AnchorPane paneReminder;
    @FXML
    private Label lblError;
    @FXML
    private Label lblErrorReminder;
    @FXML
    private ComboBox<String> cmbBookName;
    @FXML
    private DatePicker dtpDeadline;
    @FXML
    private ComboBox<String> cmbDays;
    @FXML
    private TextField txtBookName;
    @FXML
    private TextField txtBookAuthor;
    @FXML
    private TextField txtPublishingHouse;
    @FXML
    private TextField txtYear;
    @FXML
    private DatePicker dtpLoan;
    @FXML
    private DatePicker dtpReturn;
    @FXML
    private Button btnView;
    @FXML
    private Button btnCosts;
    @FXML
    private Button btnGraphic;
    @FXML
    private TableView<TableBooks> tblBooks;
    @FXML
    private Label lblMenu;
    @FXML
    private Label lblMenuClose;
    @FXML
    private AnchorPane paneMenu;
    @FXML
    private AnchorPane paneSlider;
    @FXML
    private ImageView image1;
    @FXML
    private ImageView image2;
    @FXML
    private ImageView image3;

    private String user;
    private final BooksService booksService= GetInstance.of(BooksService.class);
    private final ReminderService reminderService= GetInstance.of(ReminderService.class);



    public void start(String user)
    {
        this.user=user;
        TableColumn<TableBooks, String> columnName = new TableColumn<>("Book's name");
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<TableBooks, String> columnAuthor = new TableColumn<>("Book's author");
        columnAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        TableColumn<TableBooks, String> columnPublish = new TableColumn<>("Book's publish house");
        columnPublish.setCellValueFactory(new PropertyValueFactory<>("publish_house"));
        TableColumn<TableBooks, String> columnYear = new TableColumn<>("Book's year of appearance");
        columnYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        TableColumn<TableBooks, String> columnLoan = new TableColumn<>("Loan date");
        columnLoan.setCellValueFactory(new PropertyValueFactory<>("loan_date"));
        TableColumn<TableBooks, String> columnDay = new TableColumn<>("Day of return");
        columnDay.setCellValueFactory(new PropertyValueFactory<>("day_of_return"));
        tblBooks.getColumns().add(columnName);
        tblBooks.getColumns().add(columnAuthor);
        tblBooks.getColumns().add(columnPublish);
        tblBooks.getColumns().add(columnYear);
        tblBooks.getColumns().add(columnLoan);
        tblBooks.getColumns().add(columnDay);
        paneSlider.setTranslateX(-230);
        lblMenu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(paneSlider);

            slide.setToX(0);
            slide.play();

            paneSlider.setTranslateX(-230);

            slide.setOnFinished((ActionEvent e)-> {
                lblMenu.setVisible(false);
                lblMenuClose.setVisible(true);
            });
        });

        lblMenuClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(paneSlider);

            slide.setToX(-230);
            slide.play();

            paneSlider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)-> {
                lblMenu.setVisible(true);
                lblMenuClose.setVisible(false);
            });
        });
        for(Node node : paneMenu.getChildren())
        {
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
        btnAdd.setOnMouseEntered(e -> {
                btnAdd.setStyle("-fx-background-color: transparent;-fx-text-fill:#66b3ff; -fx-border-width: 3px;" +
                        "-fx-border-color: #66b3ff;");
                btnAdd.setTranslateX(5);
                btnAdd.setTranslateY(-5);
            javafx.scene.image.Image image = null;
            try {
                image = new javafx.scene.image.Image(Library.class.getResource("images/book_blue.png").openStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            image1.setImage(image);
            });
        btnAdd.setOnMouseExited(e -> {
                btnAdd.setStyle("-fx-background-color: transparent;-fx-text-fill: #d9e9f2; -fx-border-width: 3px;" +
                        "-fx-border-color:#d9e9f2;");
                btnAdd.setTranslateX(-5);
                btnAdd.setTranslateY(5);
            javafx.scene.image.Image image = null;
            try {
                image = new javafx.scene.image.Image(Library.class.getResource("images/book_white.png").openStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            image1.setImage(image);
            });
        btnView.setOnMouseEntered(e -> {
            btnView.setStyle("-fx-background-color: transparent;-fx-text-fill:#66b3ff; -fx-border-width: 3px;" +
                    "-fx-border-color: #66b3ff;");
            btnView.setTranslateX(5);
            btnView.setTranslateY(-5);
            javafx.scene.image.Image image = null;
            try {
                image = new javafx.scene.image.Image(Library.class.getResource("images/table_blue.png").openStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            image2.setImage(image);
        });
        btnView.setOnMouseExited(e -> {
            btnView.setStyle("-fx-background-color: transparent;-fx-text-fill: #d9e9f2; -fx-border-width: 3px;" +
                    "-fx-border-color:#d9e9f2;");
            btnView.setTranslateX(-5);
            btnView.setTranslateY(5);
            javafx.scene.image.Image image = null;
            try {
                image = new javafx.scene.image.Image(Library.class.getResource("images/table_white.png").openStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            image2.setImage(image);
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
            image3.setImage(image);
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
            image3.setImage(image);
        });


    }

    public boolean fieldsValidation(String name, String author, String house, String year, Calendar loan, Calendar date_of_return)
    {
        Calendar c= Calendar.getInstance();
        Calendar f= Calendar.getInstance();
        f.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH)+1,c.get(Calendar.DAY_OF_MONTH));
        List<Books> all = booksService.findAll();
        if(name.isBlank() || author.isBlank() || house.isBlank() || year.isBlank())
        {
            lblError.setText("Fill in all  the fields");
            Paint paint = Paint.valueOf("red");
            lblError.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
            lblError.setFont(font);
            return false;
        }
        if(year.matches("[a-zA-Z]+"))
        {
            lblError.setText("Year of appearance must contains just numbers without letters");
            Paint paint = Paint.valueOf("red");
            lblError.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
            lblError.setFont(font);
            return false;
        }
        if(Double.parseDouble(year)<1700 || Double.parseDouble(year)>Calendar.getInstance().get(Calendar.YEAR))
        {
            lblError.setText("Year of appearance must be between 1700 and present");
            Paint paint = Paint.valueOf("red");
            lblError.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
            lblError.setFont(font);
            return false;
        }
        if(loan.getTimeInMillis()>c.getTimeInMillis())
        {
            lblError.setText("The loan date cannot be in the future");
            Paint paint = Paint.valueOf("red");
            lblError.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
            lblError.setFont(font);
            return false;
        }
        if(date_of_return.getTimeInMillis()<c.getTimeInMillis())
        {
            lblError.setText("The return date cannot be in the past");
            Paint paint = Paint.valueOf("red");
            lblError.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
            lblError.setFont(font);
            return false;
        }
        if(date_of_return.getTimeInMillis()>f.getTimeInMillis())
        {
            lblError.setText("The date of return cannot be more late than one month after loan date ");
            Paint paint = Paint.valueOf("red");
            lblError.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
            lblError.setFont(font);
            return false;
        }
        if(loan.getTimeInMillis()>date_of_return.getTimeInMillis())
        {
            lblError.setText("The return date cannot be earlier than the loan date ");
            Paint paint = Paint.valueOf("red");
            lblError.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
            lblError.setFont(font);
            return false;
        }
        if(loan.getTimeInMillis()==date_of_return.getTimeInMillis())
        {
            lblError.setText("The return date cannot be the same as the loan date ");
            Paint paint = Paint.valueOf("red");
            lblError.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
            lblError.setFont(font);
            return false;
        }
      for(Books book : all)
       {
           if(book.getUser().equals(user) && book.getBook_name().equals(name) && book.getBook_author().equals(author)
           && book.getPublishing_house().equals(house) && Integer.parseInt(year)==book.getYear()) {
               lblError.setText("This book was already added ");
               Paint paint = Paint.valueOf("red");
               lblError.setTextFill(paint);
               Font font = new Font("Gadugi", 10);
               lblError.setFont(font);
               return false;
           }
       }
       return true;
    }
    public void btnAddOnClick() {
        paneAdd.setVisible(true);
        LocalDate l = LocalDate.now();
        dtpLoan.setValue(l);
        dtpReturn.setValue(l);
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
    public void btnCostsOnClick() throws IOException
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("costs.fxml"));
        Stage stage =(Stage) btnCosts.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        Costs menu = loader.getController();
        menu.start(user);
        stage.show();
    }
    public void btnBackOnClick() throws IOException, AWTException
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("menu.fxml"));
        Stage stage =(Stage) btnBack.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        Menu menu = loader.getController();
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

    public void btnNewBookOnClick()
    {
        Calendar loan = Calendar.getInstance();
        loan.set(dtpLoan.getValue().getYear(),dtpLoan.getValue().getMonthValue()-1,dtpLoan.getValue().getDayOfMonth());
        Calendar day_of_return = Calendar.getInstance();
        day_of_return.set(dtpReturn.getValue().getYear(),dtpReturn.getValue().getMonthValue()-1,dtpReturn.getValue().getDayOfMonth());
        if(fieldsValidation(txtBookName.getText(),txtBookAuthor.getText(),txtPublishingHouse.getText(),txtYear.getText()
        ,loan,day_of_return))
        {
            Books book = new Books(user,txtBookName.getText(),txtBookAuthor.getText(),txtPublishingHouse.getText(),Integer.parseInt(txtYear.getText())
                    ,loan,day_of_return);
            Books save = booksService.save(book);
            if(tblBooks.isVisible())
            {
                String l=book.getLoan_date().get(Calendar.DAY_OF_MONTH)+"."+(book.getLoan_date().get(Calendar.MONTH)+1)
                        +"."+book.getLoan_date().get(Calendar.YEAR);
                String day=book.getDate_of_return().get(Calendar.DAY_OF_MONTH)+"."+(book.getDate_of_return().get(Calendar.MONTH)+1)
                        +"."+book.getDate_of_return().get(Calendar.YEAR);
                tblBooks.getItems().add(new TableBooks(book.getBook_name(), book.getBook_author(), book.getPublishing_house()
                        , book.getYear(), l, day));

            }
            if(paneReminder.isVisible())
            {
                cmbBookName.getItems().add(book.getBook_name());
            }
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("The book has been successfully registered");
            a.show();
            paneAdd.setVisible(false);
            lblError.setText("");
            txtBookName.clear();
            txtBookAuthor.clear();
            txtPublishingHouse.clear();
            txtYear.clear();
            dtpLoan.getEditor().clear();
            dtpLoan.getEditor().clear();

        }

    }

    public void btnViewOnClick()
    {
        tblBooks.setVisible(true);
        List<Books> all = booksService.findAll();
        for(Books book : all)
        {
            String loan=book.getLoan_date().get(Calendar.DAY_OF_MONTH)+"."+(book.getLoan_date().get(Calendar.MONTH)+1)
                    +"."+book.getLoan_date().get(Calendar.YEAR);
            String day=book.getDate_of_return().get(Calendar.DAY_OF_MONTH)+"."+(book.getDate_of_return().get(Calendar.MONTH)+1)
                    +"."+book.getDate_of_return().get(Calendar.YEAR);
            TableBooks duplicate = new TableBooks(book.getBook_name(), book.getBook_author(), book.getPublishing_house()
                    , book.getYear(), loan, day);
            if(book.getUser().equals(user))
            {
                if(tblBooks.getItems().isEmpty())
                {
                    tblBooks.getItems().add(duplicate);
                }else {
                    for (TableBooks row : tblBooks.getItems()) {
                        if (!(row.getName().equals(duplicate.getName()) && row.getAuthor().equals(duplicate.getAuthor()) &&
                                row.getPublish_house().equals(duplicate.getPublish_house()))) {
                            tblBooks.getItems().add(duplicate);
                        }
                    }
                }
            }
        }
    }

    public void btnReminderOnClick()
    {
        List<Books> all = booksService.findAll();
        Calendar c= Calendar.getInstance();
        for(Books book : all)
        {
            if(book.getUser().equals(user) && book.getDate_of_return().getTimeInMillis()>c.getTimeInMillis())
            {
                if(cmbBookName.getItems().isEmpty())
                {
                    cmbBookName.getItems().add(book.getBook_name());
                }else {
                    for (String name : cmbBookName.getItems()) {
                        if (!name.equals(book.getBook_name())) {
                            cmbBookName.getItems().add(book.getBook_name());
                        }
                    }
                }

            }
        }
        LocalDate l = LocalDate.now();
        dtpDeadline.setValue(l);
        if(cmbDays.getItems().isEmpty()) {
            cmbDays.getItems().addAll("Day", "Week", "Month");
        }
        paneReminder.setVisible(true);
    }

    public void cmbBookNameOnChange()
    {
        List<Books> all = booksService.findAll();
        for(Books book : all)
        {
            if(book.getUser().equals(user) && book.getBook_name().equals(cmbBookName.getValue()))
            {
                LocalDate l= LocalDate.of(book.getDate_of_return().get(Calendar.YEAR),book.getDate_of_return().get(Calendar.MONTH)+1
                ,book.getDate_of_return().get(Calendar.DAY_OF_MONTH));
                dtpDeadline.setValue(l);
            }
        }
    }

    public boolean reminderFieldsValidation(String book,String days)
    {
        if(book == null || days == null)
        {
            lblErrorReminder.setText("Fill in all the fields ");
            Paint paint = Paint.valueOf("red");
            lblErrorReminder.setTextFill(paint);
            Font font = new Font("Gadugi", 10);
            lblErrorReminder.setFont(font);
            return false;
        }
        List<Reminder> all =  reminderService.findAll();
        String title= "Reminder you need to return: ";
        for(Reminder reminder : all)
        {
            if(reminder.getUsername().equals(user) &&reminder.getTitle().equals(title)&& reminder.getMessage().equals(book) && reminder.getDays().equals(days))
            {
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

    public void btnNewReminderOnClick()
    {
        if(reminderFieldsValidation(cmbBookName.getValue(),cmbDays.getValue()))
        {
            Calendar c = Calendar.getInstance();
            c.set(dtpDeadline.getValue().getYear(),dtpDeadline.getValue().getMonthValue()-1,dtpDeadline.getValue().getDayOfMonth());
            String title ="Reminder you need to return: ";
            String message=cmbBookName.getValue();
            Reminder reminder = new Reminder(user,title,message,c,cmbDays.getValue());
            Reminder save= reminderService.save(reminder);
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("The reminder has been successfully registered");
            a.show();
            paneReminder.setVisible(false);
            lblErrorReminder.setText("");
            cmbDays.getItems().clear();
            dtpDeadline.getEditor().clear();
        }
    }
}
