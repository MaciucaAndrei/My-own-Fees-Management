package project.licenta.entity;


import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "books_entity")
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String username;
    @Column
    private String book_name;
    @Column
    private String book_author;
    @Column
    private String publishing_house;
    @Column
    private int year;
    @Column
    private Calendar loan_date;
    @Column
    private Calendar date_of_return;

    public Books() {
    }

    public Books(String username, String book_name, String book_author, String publishing_house, int year, Calendar loan_date, Calendar date_of_return) {
        this.username = username;
        this.book_name = book_name;
        this.book_author = book_author;
        this.publishing_house = publishing_house;
        this.year = year;
        this.loan_date = loan_date;
        this.date_of_return = date_of_return;
    }

    public String getUser() {
        return username;
    }

    public void setUser(String username) {
        this.username = username;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public String getPublishing_house() {
        return publishing_house;
    }

    public void setPublishing_house(String publishing_house) {
        this.publishing_house = publishing_house;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Calendar getLoan_date() {
        return loan_date;
    }

    public void setLoan_date(Calendar loan_date) {
        this.loan_date = loan_date;
    }

    public Calendar getDate_of_return() {
        return date_of_return;
    }

    public void setDate_of_return(Calendar date_of_return) {
        this.date_of_return = date_of_return;
    }

    @Override
    public String toString() {
        return "Books{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", book_name='" + book_name + '\'' +
                ", book_author='" + book_author + '\'' +
                ", publishing_house='" + publishing_house + '\'' +
                ", year=" + year +
                ", loan_date=" + loan_date +
                ", date_of_return=" + date_of_return +
                '}';
    }


}
