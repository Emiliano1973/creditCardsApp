package ie.myinfinity.demo.creditcard.services;

import ie.myinfinity.demo.creditcard.dtos.UserDto;
import ie.myinfinity.demo.creditcard.entities.User;
import ie.myinfinity.demo.creditcard.exceptions.UserAlreadyinsertedException;
import ie.myinfinity.demo.creditcard.exceptions.UserException;

import java.util.Optional;

public interface UserService {

    void save(UserDto userDto) throws UserAlreadyinsertedException, UserException;

    Optional<User> findByUsername(String username);

    boolean matchUserAndPassword(String username, String password);
}
