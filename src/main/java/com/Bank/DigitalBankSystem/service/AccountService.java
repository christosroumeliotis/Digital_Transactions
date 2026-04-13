package com.Bank.DigitalBankSystem.service;

import com.Bank.DigitalBankSystem.entity.Account;
import com.Bank.DigitalBankSystem.entity.User;
import com.Bank.DigitalBankSystem.repository.AccountRepo;
import com.Bank.DigitalBankSystem.utils.interfaces.Utils;
import com.Bank.DigitalBankSystem.utils.interfacesImpl.UtilsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    UserService userService;

    @Autowired
    AccountRepo accountRepo;

    private final Utils utils = new UtilsImpl();

    public String createAccount(Long userId) throws Exception {
        User userFound = utils.getTheUser(userId, userService);
        Account account = Account.builder()
                .user(userFound)
                .createdAt(LocalDateTime.now())
                .balance(0.0).build();
        accountRepo.save(account);
        return userFound.getUsername() + " created a new account!";
    }

    public List<Account> findAccountsByUserId(Long userId) throws Exception {
        User userFound = utils.getTheUser(userId, userService);
        return userFound.getAccount();
    }

    public Account findAccountByAccountId(Long accountId) {
        Optional<Account> accountFound = accountRepo.findById(accountId);
        return accountFound.orElse(null);
    }
}
