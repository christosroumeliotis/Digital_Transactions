package com.Bank.DigitalBankSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double balance;
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Version
    private Long version;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", createdAt=" + createdAt +
                ", user=" + user +
                '}';
    }
}
