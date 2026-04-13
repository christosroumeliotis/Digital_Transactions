package com.Bank.DigitalBankSystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data //lombok annotation
@NoArgsConstructor
public class ErrorResponse {
    private HttpStatus httpStatus;
    private String message;
    private Instant timestamp;
}