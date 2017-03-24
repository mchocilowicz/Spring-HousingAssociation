package nsai.spring.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

/**
 * Created by Marcin on 13.06.2016.
 */
@Entity
public class Counter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotEmpty
    private String room;

    @NotEmpty
    private String name;


    private double value;

    private double difference = 0;

    public Counter(){}

    public Counter(String room,String name,double value,Flat flat){
        this.room = room;
        this.name = name;
        this.value = value;
        this.flat = flat;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flat_id")
    private Flat flat;


    public String getFlatInfo(){
        return this.flat.getBuilding().getName() + " : "+this.flat.getNumber();
    }


    public double getDifference() {
        return difference;
    }

    public void setDifference(double difference) {
        this.difference = difference;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Flat getFlat() {
        return flat;
    }

    public void setFlat(Flat flat) {
        this.flat = flat;
    }

    public void updateCounter(Counter counter){
        this.difference = counter.getValue() - this.value;
        this.value = counter.getValue();

    }

    public boolean counterNumber(){
        if(flat.getCounterList().size() > flat.getCountersLeng()){
            return true;
        }else{
            return false;
        }
    }
}
