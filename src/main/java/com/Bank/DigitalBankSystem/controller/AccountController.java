package com.Bank.DigitalBankSystem.controller;

import com.Bank.DigitalBankSystem.config.mapper.AccountMapper;
import com.Bank.DigitalBankSystem.dto.AccountDTO;
import com.Bank.DigitalBankSystem.dto.ResponsesDto.SuccessResponse;
import com.Bank.DigitalBankSystem.entity.Account;
import com.Bank.DigitalBankSystem.service.AccountService;
import com.Bank.DigitalBankSystem.utils.interfaces.Utils;
import com.Bank.DigitalBankSystem.utils.interfacesImpl.UtilsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
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

    @Autowired
    CacheManager cacheManager;

    private final Utils utils = new UtilsImpl();

    @PostMapping("/user/{userId}")
    public ResponseEntity<SuccessResponse<String>> createAccount(@PathVariable Long userId) throws Exception {

        return utils.createSuccessResponse(accountService.createAccount(userId), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<SuccessResponse<List<AccountDTO>>> getUserAccounts(@PathVariable Long userId) throws Exception {

          List<Account> accounts = accountService.findAccountsByUserId(userId);
          List<AccountDTO> accountsReturn = new ArrayList<>();
          for(Account account : accounts){
              accountsReturn.add(AccountMapper.INSTANCE.toDto(account));
          }
          return utils.createSuccessResponse(accountsReturn, HttpStatus.FOUND);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<SuccessResponse<AccountDTO>> getAccountById(@PathVariable Long accountId){
        Account account = accountService.findAccountByAccountId(accountId);
        return utils.createSuccessResponse(AccountMapper.INSTANCE.toDto(account), HttpStatus.FOUND);
    }
}
