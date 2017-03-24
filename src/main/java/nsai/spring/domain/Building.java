package nsai.spring.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Marcin on 17.05.2016.
 */
@Entity
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotEmpty
    private String name;

    @Column(name = "floor", nullable = false)

    private Long floor;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "building",fetch = FetchType.EAGER)
    private List<Flat> fltas ;


    public List<Flat> getFltas() {
        return fltas;
    }

    public void setFltas(List<Flat> fltas) {
        this.fltas = fltas;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFloor() {
        return floor;
    }

    public void setFloor(Long floor) {
        this.floor = floor;
    }

    public int flatCount(){
        return this.getFltas().size();
    }

    @Override
    public String toString() {
        return "Building{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", floor=" + floor +
                '}';
    }
}
