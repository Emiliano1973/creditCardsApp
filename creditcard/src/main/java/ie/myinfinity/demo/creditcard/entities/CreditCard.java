package ie.myinfinity.demo.creditcard.entities;

import ie.myinfinity.demo.creditcard.entities.utils.LocalDateAttributeConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "CREDIT_CARDS")
public class CreditCard implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;


    @Column(name = "CARD_CODE", nullable =false, unique = true,  length = 16)
    private String cardCode;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Convert(converter = LocalDateAttributeConverter.class)
    @Column(name = "EXPIRY_DATE")
    private LocalDate expiryDate;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
