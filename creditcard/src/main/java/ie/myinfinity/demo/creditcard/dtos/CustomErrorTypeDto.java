package ie.myinfinity.demo.creditcard.dtos;


import java.io.Serializable;

public final class CustomErrorTypeDto implements Serializable {

    private final int status;
    private final String message;
    private final String error;
    private final String path;
    private final String timestamp;

    public CustomErrorTypeDto(int status, String path, String error, String message, String timestamp) {
        super();
        this.status = status;
        this.path = path;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public String setTimestamp() {
        return timestamp;
    }

    public String getError() {
        return error;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
