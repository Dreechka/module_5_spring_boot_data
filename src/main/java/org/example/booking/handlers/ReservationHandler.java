package org.example.booking.handlers;

import org.example.booking.exceptions.ReservationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ReservationHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(value = ReservationException.class)
    public ResponseEntity handle(Exception e){
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


}
