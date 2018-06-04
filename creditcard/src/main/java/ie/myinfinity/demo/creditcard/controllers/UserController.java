package ie.myinfinity.demo.creditcard.controllers;

import ie.myinfinity.demo.creditcard.dtos.ResponseDto;
import ie.myinfinity.demo.creditcard.dtos.UserDto;
import ie.myinfinity.demo.creditcard.exceptions.UserAlreadyinsertedException;
import ie.myinfinity.demo.creditcard.exceptions.UserException;
import ie.myinfinity.demo.creditcard.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Service for insert new users, it's not necessary to be logged
 */
@RequestMapping("/api/ext/users")
@RestController
public class UserController extends AbstractController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody UserDto userDto){

        try {
            userService.save(userDto);
        } catch (UserAlreadyinsertedException e) {
            return geCustomErrorType(HttpStatus.CONFLICT.value(),"api/user", "Save user failed",
                    e.getMessage(), LocalDateTime.now());
        } catch (UserException e) {
            return geCustomErrorType(HttpStatus.BAD_REQUEST.value(),"api/user", "Save user failed",
                    e.getMessage(), LocalDateTime.now());
        }
        return new  ResponseEntity< ResponseDto>(new ResponseDto("User "+userDto.getUsername()+" created",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

}
