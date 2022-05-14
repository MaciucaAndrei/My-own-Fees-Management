package project.licenta.utils;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableBooks {
    private StringProperty name;
    private StringProperty author;
    private StringProperty publish_house;
    private IntegerProperty year;
    private StringProperty loan_date;
    private StringProperty day_of_return;

    public TableBooks(String name, String author, String publish_house, int year, String loan_date, String day_of_return) {
        nameProperty().set(name);
        authorProperty().set(author);
        publish_houseProperty().set(publish_house);
        yearProperty().set(year);
        loan_dateProperty().set(loan_date);
        day_of_returnProperty().set(day_of_return);
    }

    public String getName() {return nameProperty().get();}
    public void setName(String name) {nameProperty().set(name);}
    public String getAuthor() {return authorProperty().get();}
    public void setAuthor(String author) {authorProperty().set(author);}
    public String getPublish_house() {return publish_houseProperty().get();}
    public void setPublish_house(String publish_house) {publish_houseProperty().set(publish_house);}
    public int getYear() {return yearProperty().get();}
    public void setYear(int year) {yearProperty().set(year);}
    public String getLoan_date() {return loan_dateProperty().get();}
    public void setLoan_date(String loan_date) {loan_dateProperty().set(loan_date);}
    public String getDay_of_return() {return day_of_returnProperty().get();}
    public void setDay_of_return(String day_of_return){day_of_returnProperty().set(day_of_return);}
    public StringProperty nameProperty() {
        if (name == null) name = new SimpleStringProperty(this, "name");
        return name;
    }
    public StringProperty authorProperty() {
        if (author == null) author = new SimpleStringProperty(this, "author");
        return author;
    }

    public StringProperty publish_houseProperty() {
        if (publish_house == null) publish_house = new SimpleStringProperty(this, "publish_house");
        return publish_house;
    }

    public IntegerProperty yearProperty() {
        if (year == null) year = new SimpleIntegerProperty(this, "year");
        return year;
    }

    public StringProperty loan_dateProperty() {
        if (loan_date == null) loan_date = new SimpleStringProperty(this, "loan_date");
        return loan_date;
    }

    public StringProperty day_of_returnProperty() {
        if (day_of_return == null) day_of_return = new SimpleStringProperty(this, "day_of_return");
        return day_of_return;
    }

}
