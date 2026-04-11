package com.Bank.DigitalBankSystem.repository;

import com.Bank.DigitalBankSystem.entity.Account;
import com.Bank.DigitalBankSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, Long> {
}
