package project.licenta.entity;


import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "taxes_entity")
public class Taxes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String username;
    @Column
    private String  payment_type;
    @Column
    private Calendar payment_date;
    @Column
    private double payment_amount;

    public Taxes()
    {

    }

    public Taxes(String username,String payment_type,Calendar payment_date,double payment_amount)
    {
        this.username=username;
        this.payment_type=payment_type;
        this.payment_date=payment_date;
        this.payment_amount=payment_amount;
    }

    public String getUser(){return username;}
    public void setUser(String user){this.username=username;}
    public String getPayment_type(){return payment_type;}
    public void setPayment_type(String payment_type){this.payment_type=payment_type;}
    public Calendar getPayment_date(){return payment_date;}
    public void setPayment_date(Calendar payment_date){this.payment_date=payment_date;}
    public double getPayment_amount(){return payment_amount;}
    public void setPayment_amount(double payment_amount){this.payment_amount=payment_amount;}

    @Override
    public String toString(){
        return "Taxes{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", payment_type='" + payment_type + '\'' +
                ", payment_date='" + payment_date.get(Calendar.DAY_OF_MONTH)+"."+payment_date.get(Calendar.MONTH)+"."+
                payment_date.get(Calendar.YEAR)+ '\'' +
                ", payment_amount='" + payment_amount + '\'' +
                '}';
    }
}
