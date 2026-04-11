package com.Bank.DigitalBankSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Double balance;
    private LocalDateTime createdAt;
}
