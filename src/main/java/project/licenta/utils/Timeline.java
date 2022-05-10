package project.licenta.utils;

import javafx.beans.property.SimpleStringProperty;

public class Timeline {

    private final SimpleStringProperty hour= new SimpleStringProperty("");
    private final SimpleStringProperty monday = new SimpleStringProperty("");
    private final SimpleStringProperty tuesday = new SimpleStringProperty("");
    private final SimpleStringProperty wednesday = new SimpleStringProperty("");
    private final SimpleStringProperty thursday = new SimpleStringProperty("");
    private final SimpleStringProperty friday = new SimpleStringProperty("");

    public Timeline(){
        this("","","","","","");
    }

    public Timeline(String hour,String monday,String tuesday,String wednesday,String thursday,String friday){

       setHour(hour);
       setMonday(monday);
       setTuesday(tuesday);
       setWednesday(wednesday);
       setThursday(thursday);
       setFriday(friday);
    }

    public SimpleStringProperty hourProperty(){return hour;}
    public void setHour(String hour){this.hour.set(hour);}
    public SimpleStringProperty mondayProperty(){return monday;}
    public void setMonday(String monday){this.monday.set(monday);}
    public SimpleStringProperty tuesdayProperty(){return tuesday;}
    public void setTuesday(String tuesday){this.tuesday.set(tuesday);}
    public SimpleStringProperty wednesdayProperty(){return wednesday;}
    public void setWednesday(String wednesday){this.wednesday.set(wednesday);}
    public SimpleStringProperty thursdayProperty(){return thursday;}
    public void setThursday(String thursday){this.thursday.set(thursday);}
    public SimpleStringProperty fridayProperty(){return friday;}
    public void setFriday(String friday){this.friday.set(friday);}
}
