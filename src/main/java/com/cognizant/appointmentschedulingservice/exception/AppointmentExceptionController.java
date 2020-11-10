package com.cognizant.appointmentschedulingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppointmentExceptionController {
    @ExceptionHandler(value = DcNotFoundException.class)
    public ResponseEntity<Object> exception(DcNotFoundException exception){
        return  new ResponseEntity<>("Dc Not Found", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = DcSlotNotFoundException.class)
    public ResponseEntity<Object> exception(DcSlotNotFoundException exception){
        return new ResponseEntity<>("DC Slot Not Found", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = DcSlotUnavailableException.class)
    public ResponseEntity<Object> exception(DcSlotUnavailableException exception){
        return new ResponseEntity<>("Maximum Trucks Reached to particular slot",HttpStatus.TOO_MANY_REQUESTS);
    }
    @ExceptionHandler(value = PONotFoundException.class)
    public ResponseEntity<Object> exception(PONotFoundException exception){
        return new ResponseEntity<>("Purchase Order(PO) Not Found",HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = TruckNotFoundException.class)
    public ResponseEntity<Object> exception(TruckNotFoundException exception){
        return new ResponseEntity<>("Purchase Order(PO) Not Found",HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = AppointmentNotFoundException.class)
    public ResponseEntity<Object> exception(AppointmentNotFoundException exception){
        return new ResponseEntity<>("There is no Appointment Scheduled for given Id",HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = TruckAlreadyAllocatedException.class)
    public ResponseEntity<Object> exception(TruckAlreadyAllocatedException exception){
        return new ResponseEntity<>("Truck Already Allocated to particular slot",HttpStatus.TOO_MANY_REQUESTS);
    }

}
