package project.licenta.entity;


import javax.persistence.*;

@Entity
@Table(name = "allsubjects_entity")
public class AllSubjects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String code;

    @Column
    private String subject_name;

    @Column
    private int year;

    @Column
    private int semester;

    public AllSubjects()
    {

    }

    public AllSubjects(String code,String subject_name, int year,int semester )
    {
        this.code=code;
        this.subject_name=subject_name;
        this.year=year;
        this.semester=semester;
    }

    public String getCode(){return code;}
    public void setCode(String code){this.code=code;}
    public String getSubject_name(){return subject_name;}
    public void setSubject_name(String subject_name){this.subject_name=subject_name;}
    public int getYear(){return year;}
    public void setYear(int year){this.year=year;}
    public int getSemester(){return semester;}
    public void setSemester(int semester){this.semester=semester;}

}
