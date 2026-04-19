package com.Bank.DigitalBankSystem.dto;

import com.Bank.DigitalBankSystem.entity.Account;
import com.Bank.DigitalBankSystem.entity.User;
import com.Bank.DigitalBankSystem.enum_.TransactionTypeEnum;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private TransactionTypeEnum type;
    @Positive(message = "Negative amount not valid")
    private Double amount;
    private LocalDateTime transactionTime;
    @NotNull(message = "Sender cannot be null")
    private Long senderId;
    @NotNull(message = "Sender's account cannot be null")
    private Long senderAccountId;
    private Long receiverId;
    private Long receiverAccountId;
}
