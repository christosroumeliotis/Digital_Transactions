package com.Bank.DigitalBankSystem.dto.ResponsesDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class SuccessResponse<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T response;
    private Instant timestampOfCall;
}
