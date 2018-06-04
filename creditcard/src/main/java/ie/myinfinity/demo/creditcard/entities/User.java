package ie.myinfinity.demo.creditcard.entities;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "USERS")
public class User  implements Serializable{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Role.class)
    @JoinColumn(name = "ROLE_ID", nullable = false, referencedColumnName = "ID")
    private Role role;


    public User() {
        super();
    }

    public User(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
