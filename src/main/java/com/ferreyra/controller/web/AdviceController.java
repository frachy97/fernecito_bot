package com.ferreyra.controller.web;

import com.ferreyra.dto.ErrorResponseDto;
import com.ferreyra.exceptions.InvalidRequestException;
import com.ferreyra.exceptions.RecordAlreadyExistsException;
import com.ferreyra.exceptions.RecordNotExistsException;
import com.ferreyra.exceptions.ValidationException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class AdviceController extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ValidationException.class)
    public ErrorResponseDto handleLoginException(ValidationException exc) {
        return new ErrorResponseDto(1, exc.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RecordNotExistsException.class)
    public ErrorResponseDto handleUserNotExists(RecordNotExistsException exc) {
        return new ErrorResponseDto(2, exc.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(RecordAlreadyExistsException.class)
    public ErrorResponseDto handleUserAlreadyExists(RecordAlreadyExistsException exc) {
        return new ErrorResponseDto(3, exc.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidRequestException.class)
    public ErrorResponseDto handleInvalidRequestException(InvalidRequestException exc) {
        return new ErrorResponseDto(4, exc.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(GenericJDBCException.class)
    public ErrorResponseDto handleSQLException(GenericJDBCException exc) {
        return new ErrorResponseDto(5, "SQL conflict: " + exc.getSQLException().getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponseDto handleConstraintViolationException(ConstraintViolationException exc) {
        return new ErrorResponseDto(6, "SQL constraint conflict: " + exc.getConstraintName() + " it's already used");
    }

}