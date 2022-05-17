package project.licenta;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import project.licenta.entity.Books;
import project.licenta.entity.Taxes;
import project.licenta.service.BooksService;
import project.licenta.utils.GetInstance;
import project.licenta.utils.TableBooks;

import java.awt.*;
import java.awt.print.Book;
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
    private Button btnNewBook;
    @FXML
    private AnchorPane paneAdd;
    @FXML
    private Label lblError;
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
    private TableView<TableBooks> tblBooks;

    private String user;
    private BooksService booksService= GetInstance.of(BooksService.class);



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
    }

    public boolean fieldsValidation(String name, String author, String house, String year, Calendar loan, Calendar date_of_return)
    {
        Calendar c= Calendar.getInstance();
        Calendar f= Calendar.getInstance();
        f.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH+1),c.get(Calendar.DAY_OF_MONTH));
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
            lblError.setText("Year of appearence must contains just numbers without letters");
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
        if(date_of_return.getTimeInMillis()>f.getTimeInMillis())
        {
            lblError.setText("The date of return cannot be more late than one month after loan date ");
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
    public void btnAddOnClick(ActionEvent e) {
        paneAdd.setVisible(true);
        LocalDate l = LocalDate.now();
        dtpLoan.setValue(l);
        dtpReturn.setValue(l);
    }
    public void btnBackOnClick(ActionEvent e) throws IOException, AWTException
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
        Stage logout= (Stage) btnLogout.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login.fxml"));
        Scene scene= new Scene(fxmlLoader.load());
        logout.setScene(scene);
    }

    public void btnNewBookOnClick(ActionEvent e)
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

    public void btnViewOnClick(ActionEvent e)
    {
        tblBooks.setVisible(true);
        List<Books> all = booksService.findAll();
        for(Books book : all)
        {
            if(book.getUser().equals(user))
            {
                String loan=book.getLoan_date().get(Calendar.DAY_OF_MONTH)+"."+(book.getLoan_date().get(Calendar.MONTH)+1)
                        +"."+book.getLoan_date().get(Calendar.YEAR);
                String day=book.getDate_of_return().get(Calendar.DAY_OF_MONTH)+"."+(book.getDate_of_return().get(Calendar.MONTH)+1)
                        +"."+book.getDate_of_return().get(Calendar.YEAR);
                tblBooks.getItems().add(new TableBooks(book.getBook_name(), book.getBook_author(), book.getPublishing_house()
                        , book.getYear(), loan, day));
            }
        }
        tblBooks.setPrefWidth(Region.USE_COMPUTED_SIZE);
    }
}
