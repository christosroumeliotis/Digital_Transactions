package com.Bank.DigitalBankSystem.dto.ResponsesDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
@NoArgsConstructor
public class SuccessResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object response;
    private Instant timestamp;
}
