package ie.myinfinity.demo.creditcard.exceptions;

//exception is launche if the user is already present
public class UserException extends Exception {


    public UserException(String message) {
        super(message);
    }
}
