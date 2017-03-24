package nsai.spring.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Marcin on 13.06.2016.
 */

@Entity
public class Payment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    private boolean paid = false;


    private int year;

    private int month;

    private int day;

    private Date paidDate;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "costs_id")
    private Costs costs;

    public Payment(){}

    public Payment(double price, boolean paid, int year, int month, int day, User user, Costs costs) {
        this.price = price;
        this.paid = paid;
        this.year = year;
        this.month = month;
        this.day = day;
        this.user = user;
        this.costs = costs;
    }

    private double price;

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Costs getCosts() {
        return costs;
    }

    public void setCosts(Costs costs) {
        this.costs = costs;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCostDate(){
        return this.costs.getAvilable();
    }

    public String getUserName(){
        return this.user.getEmail();
    }
}
