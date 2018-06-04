package ie.myinfinity.demo.creditcard.controllers;

import ie.myinfinity.demo.creditcard.dtos.CustomErrorTypeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public abstract class AbstractController {

    /*
        return the custom error in json
     */



    protected ResponseEntity<CustomErrorTypeDto> geCustomErrorType(int status,
                                                                 String path,
                                                                 String error,
                                                                 String message,
                                                                 LocalDateTime timestamp) {


        return new ResponseEntity<CustomErrorTypeDto>(new CustomErrorTypeDto(status, path, error, message, timestamp.toString()), HttpStatus.valueOf(status));
    }

}
