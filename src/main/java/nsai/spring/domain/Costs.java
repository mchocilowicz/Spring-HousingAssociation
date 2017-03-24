package nsai.spring.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

/**
 * Created by Marcin on 13.06.2016.
 */

@Entity
public class Costs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    private Date published;

    private String avilable;


    private double electricity;


    private double gas;


    private double centralHeating;


    private double hotWater;


    private double coldWater;


    private double repairFund;


    private double operation;


    private double extraSpace;


    private double counter;


    private double urbanWaste;


    private double fee;


    private double readingCounter;


    private double garage;


    private double parkingSlot;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public String getAvilable() {
        return avilable;
    }

    public void setAvilable(String avilable) {
        this.avilable = avilable;
    }

    public double getElectricity() {
        return electricity;
    }

    public void setElectricity(double electricity) {
        this.electricity = electricity;
    }

    public double getGas() {
        return gas;
    }

    public void setGas(double gas) {
        this.gas = gas;
    }

    public double getCentralHeating() {
        return centralHeating;
    }

    public void setCentralHeating(double centralHeating) {
        this.centralHeating = centralHeating;
    }

    public double getHotWater() {
        return hotWater;
    }

    public void setHotWater(double hotWater) {
        this.hotWater = hotWater;
    }

    public double getColdWater() {
        return coldWater;
    }

    public void setColdWater(double coldWater) {
        this.coldWater = coldWater;
    }

    public double getRepairFund() {
        return repairFund;
    }

    public void setRepairFund(double repairFund) {
        this.repairFund = repairFund;
    }

    public double getOperation() {
        return operation;
    }

    public void setOperation(double operation) {
        this.operation = operation;
    }

    public double getExtraSpace() {
        return extraSpace;
    }

    public void setExtraSpace(double extraSpace) {
        this.extraSpace = extraSpace;
    }

    public double getCounter() {
        return counter;
    }

    public void setCounter(double counter) {
        this.counter = counter;
    }

    public double getUrbanWaste() {
        return urbanWaste;
    }

    public void setUrbanWaste(double urbanWaste) {
        this.urbanWaste = urbanWaste;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getReadingCounter() {
        return readingCounter;
    }

    public void setReadingCounter(double readingCounter) {
        this.readingCounter = readingCounter;
    }

    public double getGarage() {
        return garage;
    }

    public void setGarage(double garage) {
        this.garage = garage;
    }

    public double getParkingSlot() {
        return parkingSlot;
    }

    public void setParkingSlot(double parkingSlot) {
        this.parkingSlot = parkingSlot;
    }
}