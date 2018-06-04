package ie.myinfinity.demo.creditcard.security.common;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumeration of REST Error types.
 *
 */
public enum ErrorCode {
    GLOBAL(2),
    AUTHENTICATION(10),
    JWT_TOKEN_EXPIRED(11),
    BAD_CREDENTIALS(12);

    private final int errorCode;

    ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }
}
