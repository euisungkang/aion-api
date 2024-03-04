package aion.exception.handler;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import aion.exception.*;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(ex.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .timestamp(ZonedDateTime.now())
                .cause(ex)
                .build();
        return new ResponseEntity<Object>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Object> handleInvalidInput(InvalidInputException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(ex.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .timestamp(ZonedDateTime.now())
                .cause(ex)
                .build();
        return new ResponseEntity<Object>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RESTConnectionException.class)
    public ResponseEntity<Object> handleAIConnection(RESTConnectionException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(ex.getMessage())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(ZonedDateTime.now())
                .cause(ex)
                .build();
        return new ResponseEntity<Object>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResponseProcessingException.class)
    public ResponseEntity<Object> handleResponseProcessing(ResponseProcessingException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(ex.getMessage())
                .httpStatus(HttpStatus.PROCESSING)
                .timestamp(ZonedDateTime.now())
                .cause(ex)
                .build();
        return new ResponseEntity<Object>(errorResponse, HttpStatus.PROCESSING);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> handleDataAccess(DataAccessException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(ex.getMessage())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(ZonedDateTime.now())
                .cause(ex)
                .build();
        return new ResponseEntity<Object>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
