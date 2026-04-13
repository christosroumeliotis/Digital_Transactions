package com.Bank.DigitalBankSystem.controller;

import com.Bank.DigitalBankSystem.dto.AccountDTO;
import com.Bank.DigitalBankSystem.dto.ResponsesDto.SuccessResponse;
import com.Bank.DigitalBankSystem.entity.Account;
import com.Bank.DigitalBankSystem.service.AccountService;
import com.Bank.DigitalBankSystem.utils.interfaces.Utils;
import com.Bank.DigitalBankSystem.utils.interfacesImpl.UtilsImpl;
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

    private final Utils utils = new UtilsImpl();

    @PostMapping("/user/{userId}")
    public ResponseEntity<SuccessResponse> createAccount(@PathVariable Long userId) throws Exception {

          //return accountService.createAccount(userId);
        return utils.createSuccessResponse(accountService.createAccount(userId), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AccountDTO>> getUserAccounts(@PathVariable Long userId) throws Exception {
//        try {
//            List<Account> accounts = accountService.findAccountsByUserId(userId);
//            List<AccountDTO> accountsReturn = new ArrayList<>();
//            for(Account account : accounts){
//                accountsReturn.add(AccountDTO.builder().createdAt(account.getCreatedAt()).balance(account.getBalance()).build());
//            }
//            return ResponseEntity.ok(accountsReturn);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
          List<Account> accounts = accountService.findAccountsByUserId(userId);
          List<AccountDTO> accountsReturn = new ArrayList<>();
          for(Account account : accounts){
             accountsReturn.add(AccountDTO.builder().createdAt(account.getCreatedAt()).balance(account.getBalance()).build());
          }
          return ResponseEntity.ok(accountsReturn);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long accountId){
        Account account = accountService.findAccountByAccountId(accountId);
        if(account == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        return ResponseEntity.ok(AccountDTO.builder().createdAt(account.getCreatedAt()).balance(account.getBalance()).build());
    }
}
