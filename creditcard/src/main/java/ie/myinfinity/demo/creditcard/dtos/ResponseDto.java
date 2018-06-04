package ie.myinfinity.demo.creditcard.dtos;

import java.io.Serializable;

public class ResponseDto implements Serializable {

    private final String message;
    private final int responseCode;

    public ResponseDto(String message, int responseCode) {
        this.message = message;
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
