package ie.myinfinity.demo.creditcard.dtos;

import java.io.Serializable;

/**
 * Contains the input data for the search reaquests
 */
public class CreditCardRequestDto implements Serializable {

    private final String username;
    private final String cardCode;
    private final String role;

    public CreditCardRequestDto(String username,  String role, String cardCode) {
        super();
        this.username = username;
        this.role = role;
        this.cardCode = cardCode;
    }


    public String getUsername() {
        return username;
    }

    public String getCardCode() {
        return cardCode;
    }

    public String getRole() {
        return role;
    }

}
