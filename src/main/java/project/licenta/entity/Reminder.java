package project.licenta.entity;


import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "reminder_entity")
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String username;
    @Column
    private String title;
    @Column
    private String message;
    @Column
    private Calendar deadline;
    @Column
    private String days;


    public Reminder() {

    }

    public Reminder(String username, String title, String message, Calendar deadline, String days) {
        this.username = username;
        this.title = title;
        this.message = message;
        this.deadline = deadline;
        this.days = days;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Calendar getDeadline() {
        return deadline;
    }

    public void setDeadline(Calendar deadline) {
        this.deadline = deadline;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", deadline=" + deadline +
                ", days=" + days +
                '}';
    }
}
