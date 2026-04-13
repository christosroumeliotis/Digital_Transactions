package com.Bank.DigitalBankSystem.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(force = true)
@Data
public class NotValidAmountException extends CustomException {

    public NotValidAmountException(String message) {
        super(message, Instant.now(), HttpStatus.BAD_REQUEST);
    }
}
