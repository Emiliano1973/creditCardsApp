package ie.myinfinity.demo.creditcard.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ROLES")
public class Role  implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "ROLE", nullable = false, unique = true)
    private String description;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}