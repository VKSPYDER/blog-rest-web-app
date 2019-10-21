package com.kv.blog.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class BlogExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity recordNotFoundException(RecordNotFoundException ex) {
        return new ResponseEntity<>(ex != null ? ex.getBlogExceptionMessage() : new RecordNotFoundException("Record Not Found").getBlogExceptionMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceUnavailableException.class)
    public ResponseEntity resourceUnavailableException(ResourceUnavailableException ex) {
        return new ResponseEntity<>(ex == null ? new ResourceUnavailableException("Service Unavailable").getBlogExceptionMessage() : ex.getBlogExceptionMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidUserRequestException.class)
    public ResponseEntity invalidUserRequestException(InvalidUserRequestException ex) {
        return new ResponseEntity<>(ex != null ? ex.getBlogExceptionMessage() : new InvalidUserRequestException("Bad Request").getBlogExceptionMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity globleExcpetionHandler(Exception ex) {
        return new ResponseEntity<>(new BlogExceptionMessage("UNKOWN_ERROR", ex != null ? ex.getMessage() : "Unknown error occured", "", BlogExceptionMessage.AppErrorType.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
