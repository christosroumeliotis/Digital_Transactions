package com.Bank.DigitalBankSystem.utils.interfaces;

import com.Bank.DigitalBankSystem.entity.Account;
import com.Bank.DigitalBankSystem.entity.User;
import com.Bank.DigitalBankSystem.service.AccountService;
import com.Bank.DigitalBankSystem.service.UserService;

public interface Utils {

    User getTheUser(Long userId, UserService userService) throws Exception;

    Account getTheAccountOfUser(Long userId, Long accountId, AccountService accountService) throws Exception;
}
