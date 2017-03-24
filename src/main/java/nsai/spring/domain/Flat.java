package nsai.spring.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Flat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "number", nullable = false)
    private int number;


    @Column(name = "room", nullable = false)
    private int room;

    @Column(name = "area", nullable = false)
    private double area;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bulding_id")
    private Building building;

    private boolean used = false;


    private int countersNumber;

    private boolean garage;


    private int extraSpace;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "flat", orphanRemoval = true)
    private User user;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "flat",fetch = FetchType.EAGER)
    private List<Counter> counterList;

    public String getFlat(){
        return  this.building.getName() +" : " +this.number;
    }

    public double getCounterValue(String name){
        double ilosc = 0;
        for(Counter c : this.counterList){
            if(c.getName() == name){
                ilosc += c.getDifference();
            }
        }
        return ilosc;
    }
    public int getCountersLeng(){
        return this.counterList.size();
    }

    public boolean isGarage() {
        return garage;
    }

    public void setGarage(boolean garage) {
        this.garage = garage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getCountersNumber() {
        return countersNumber;
    }

    public void setCountersNumber(int countersNumber) {
        this.countersNumber = countersNumber;
    }

    public int getExtraSpace() {
        return extraSpace;
    }

    public void setExtraSpace(int extraSpace) {
        this.extraSpace = extraSpace;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Counter> getCounterList() {
        return counterList;
    }

    public void setCounterList(List<Counter> counterList) {
        this.counterList = counterList;
    }
}
