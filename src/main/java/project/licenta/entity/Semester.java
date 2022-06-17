package project.licenta.entity;

import javax.persistence.*;

@Entity
@Table(name = "Semester")
public class Semester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String user;

    @Column
    private String university;

    @Column
    private String college;

    @Column
    private String department;

    @Column
    private String university_year;

    @Column
    private int year;

    @Column
    private int semester;

    @Column
    private boolean taxes;


    public Semester() {

    }

    public Semester(String user, String university, String college, String department, String university_year, int year, int semester, boolean taxes) {
        this.user = user;
        this.university = university;
        this.college = college;
        this.department = department;
        this.university_year = university_year;
        this.year = year;
        this.semester = semester;
        this.taxes = taxes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUniversity_year() {
        return university_year;
    }

    public void setUniversity_year(String university_year) {
        this.university_year = university_year;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public boolean getTaxes() {
        return taxes;
    }

    public void setTaxes(boolean taxes) {
        this.taxes = taxes;
    }

    @Override
    public String toString() {
        return "Semester{" +
                "id=" + id +
                ", username='" + user + '\'' +
                ", university='" + university + '\'' +
                ", college='" + college + '\'' +
                ", department='" + department + '\'' +
                ", university year='" + university_year + '\'' +
                ", year='" + year + '\'' +
                ", semester='" + semester + '\'' +
                ", taxes='" + taxes + '\'' +
                '}';
    }

}
