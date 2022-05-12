package project.licenta.entity;


import javax.persistence.*;
import java.util.HashMap;

@Entity
@Table(name = "subjects_entity")
public class Subjects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String user;

    @Column
    private String semester;

    @Column
    private String subject_name;


    public Subjects()
    {

    }

    public Subjects(String user,String semester,String subject_name)
    {
        this.user=user;
        this.semester=semester;
        this.subject_name=subject_name;
    }
    public String getUser()
    {
        return user;
    }
    public void setUser(String user)
    {
        this.user=user;
    }

    public String getSemester()
    {
        return semester;
    }
    public void setSemester(String semester)
    {
        this.semester=semester;
    }
    public String getSubject_name()
    {
        return subject_name;
    }
    public void setSubject_name(String subject_name)
    {
        this.subject_name=subject_name;
    }

    @Override
    public String toString()
    {
        return "Subjects{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", semester='" + semester + '\'' +
                ", subject_name='" + subject_name + '\'' +
                '}';
    }


}
