package com.Bank.DigitalBankSystem.entity;

import com.Bank.DigitalBankSystem.enum_.TransactionTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Type not valid!")
    private TransactionTypeEnum type;

    private LocalDateTime transactionTime;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "sender_account_id")
    private Account senderAccount;
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;
    @ManyToOne
    @JoinColumn(name = "receiver_account_id")
    private Account receiverAccount;


}
