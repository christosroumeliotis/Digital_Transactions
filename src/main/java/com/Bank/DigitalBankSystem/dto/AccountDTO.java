package com.Bank.DigitalBankSystem.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Double balance;
    private LocalDateTime createdAt;
}
