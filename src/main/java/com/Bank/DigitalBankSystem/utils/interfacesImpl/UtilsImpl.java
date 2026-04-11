package com.Bank.DigitalBankSystem.utils.interfacesImpl;

import com.Bank.DigitalBankSystem.entity.Account;
import com.Bank.DigitalBankSystem.entity.User;
import com.Bank.DigitalBankSystem.service.AccountService;
import com.Bank.DigitalBankSystem.service.UserService;
import com.Bank.DigitalBankSystem.utils.interfaces.Utils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class UtilsImpl implements Utils {

    @Override
    public User getTheUser(Long userId, UserService userService) throws Exception {
        Optional<User> user = userService.findUser(userId);
        if (user.isEmpty())
            throw new Exception("User with ID: " + userId + " not found!");
        return user.get();
    }

    @Override
    public Account getTheAccountOfUser(Long accountId, Long userId, AccountService accountService) throws Exception {
        List<Account> accounts =  accountService.findAccountsByUserId(userId);
        if (accounts==null) return null;
        for(Account account : accounts){
            if(Objects.equals(account.getId(), accountId)){
                return account;
            }
        }
        throw new Exception("Account with ID: " + accountId + " for user with ID: " + userId + " not found!");
    }
}
