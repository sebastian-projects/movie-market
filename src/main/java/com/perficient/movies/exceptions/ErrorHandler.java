package com.perficient.movies.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ControllerAdvice
public class ErrorHandler {
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exc) {

        ErrorCodesEnum errorCode = exc.getCode();
        ErrorResponse error = new ErrorResponse(errorCode.name(), errorCode.getCode());

        return new ResponseEntity<>(error, errorCode.getStatus());
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintsViolation(ConstraintViolationException exc) {

        ErrorResponse error = new ErrorResponse(ErrorCodesEnum.DATA_INCOMPLETE.name(), ErrorCodesEnum.DATA_INCOMPLETE.getCode());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
