package com.Bank.DigitalBankSystem.controller;

import com.Bank.DigitalBankSystem.dto.AccountDTO;
import com.Bank.DigitalBankSystem.entity.Account;
import com.Bank.DigitalBankSystem.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<String> createAccount(@PathVariable Long userId){
        try {
            return accountService.createAccount(userId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AccountDTO>> getUserAccounts(@PathVariable Long userId){
        try {
            List<Account> accounts = accountService.findAccountsByUserId(userId);
            List<AccountDTO> accountsReturn = new ArrayList<>();
            for(Account account : accounts){
                accountsReturn.add(AccountDTO.builder().createdAt(account.getCreatedAt()).balance(account.getBalance()).build());
            }
            return ResponseEntity.ok(accountsReturn);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long accountId){
        Account account = accountService.findAccountByAccountId(accountId);
        if(account == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        return ResponseEntity.ok(AccountDTO.builder().createdAt(account.getCreatedAt()).balance(account.getBalance()).build());
    }
}
