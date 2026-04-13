package com.Bank.DigitalBankSystem.exception;

import com.Bank.DigitalBankSystem.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoRecordFoundException.class, NotValidAmountException.class, NotEnoughBalanceException.class})
    @ResponseBody
    public ResponseEntity<ErrorResponse> hancleCustomExceptions(CustomException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setHttpStatus(ex.getHttpStatus());
        errorResponse.setTimestamp(ex.getTimestamp());
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

}
