package com.Bank.DigitalBankSystem.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(force = true)
@Data
public class CustomException extends RuntimeException {

    private HttpStatus httpStatus;
    private String message;
    private Instant timestamp;

    public CustomException(String message, Instant timestamp, HttpStatus httpStatus) {
        super(message);
        this.message=message;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }
}