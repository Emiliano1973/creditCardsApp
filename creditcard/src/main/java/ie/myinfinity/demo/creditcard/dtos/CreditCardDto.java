package ie.myinfinity.demo.creditcard.dtos;

import java.io.Serializable;

public class CreditCardDto implements Serializable {

    private long id=0;
    private String cardCode;

    private String name;

    private int expiryMonth;

    private int expiryYear;

    public CreditCardDto() {
        super();
    }

    public CreditCardDto(String cardCode, String name, int expiryMonth, int expiryYear) {
        this();
        this.cardCode = cardCode;
        this.name = name;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;

    }
    public CreditCardDto(long id, String cardCode, String name, int expiryMonth, int expiryYear) {
        this(cardCode, name, expiryMonth, expiryYear);
        this.id=id;
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

    public int getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(int expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public int getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(int expiryYear) {
        this.expiryYear = expiryYear;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
