package com.Bank.DigitalBankSystem.service;

import com.Bank.DigitalBankSystem.dto.TransactionDTO;
import com.Bank.DigitalBankSystem.entity.Account;
import com.Bank.DigitalBankSystem.entity.Transaction;
import com.Bank.DigitalBankSystem.entity.User;
import com.Bank.DigitalBankSystem.enum_.TransactionTypeEnum;
import com.Bank.DigitalBankSystem.exception.NotEnoughBalanceException;
import com.Bank.DigitalBankSystem.exception.NotValidAmountException;
import com.Bank.DigitalBankSystem.repository.AccountRepo;
import com.Bank.DigitalBankSystem.repository.TransactionRepository;
import com.Bank.DigitalBankSystem.utils.interfaces.Utils;
import com.Bank.DigitalBankSystem.utils.interfacesImpl.UtilsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepo accountRepo;

    private final Utils utils = new UtilsImpl();

    @Transactional
    @CacheEvict(value = "account", key = "#transactionDto.senderAccountId")
    @Caching(evict = {
            @CacheEvict(value = "account", key = "#transactionDto.senderAccountId"),

            @CacheEvict(value = "user_accounts", key = "#transactionDto.senderId")
    })
    public Transaction depositWithdraw(TransactionDTO transactionDto) throws Exception {

        Double amount = transactionDto.getAmount();
        if (amount < 0) throw new NotValidAmountException("Negative amount not valid");

        Long userSenderId = transactionDto.getSenderId();
        User senderUser = utils.getTheUser(userSenderId, userService);
        Long accountSenderId = transactionDto.getSenderAccountId();
        Account senderAccount = utils.getTheAccountOfUser(accountSenderId, userSenderId, accountService);

        if(transactionDto.getType().equals(TransactionTypeEnum.WITHDRAW)){
            if(senderAccount.getBalance() < amount){
                throw new NotEnoughBalanceException("Balance less than " + amount);
            }
            amount = -amount;
        }

        senderAccount.setBalance(senderAccount.getBalance() + amount);
        accountRepo.save(senderAccount);

        Transaction transaction = Transaction.builder()
                .transactionTime(LocalDateTime.now())
                .amount(amount)
                .sender(senderUser)
                .senderAccount(senderAccount)
                .type(transactionDto.getType())
                .build();
        transactionRepository.save(transaction);
        return transaction;
    }

    @Transactional//If throws an exception before the transaction is saved, we rollback
    @Caching(evict = {
            @CacheEvict(value = "account",
                    key = "#transactionDto.senderAccountId"),

            @CacheEvict(value = "account",
                    key = "#transactionDto.receiverAccountId"),

            @CacheEvict(value = "user_accounts", key = "#transactionDto.senderId"),

            @CacheEvict(value = "user_accounts", key = "#transactionDto.receiverId")
    })
    public Transaction sendMoney(TransactionDTO transactionDto) throws Exception {

        Double amount = transactionDto.getAmount();
        if (amount < 0) throw new NotValidAmountException("Negative amount not valid");

        Long userSenderId = transactionDto.getSenderId();
        User senderUser = utils.getTheUser(userSenderId, userService);
        Long accountSenderId = transactionDto.getSenderAccountId();
        Account senderAccount = utils.getTheAccountOfUser(accountSenderId, userSenderId, accountService);

        Long userReceiverId = transactionDto.getReceiverId();
        User receiverUser = utils.getTheUser(userReceiverId, userService);
        Long accountReceiverId = transactionDto.getReceiverAccountId();
        Account receiverAccount = utils.getTheAccountOfUser(accountReceiverId, userReceiverId, accountService);

        if(senderAccount.getBalance() < amount){
            throw new NotEnoughBalanceException("Balance less than " + amount);
        }

        senderAccount.setBalance(senderAccount.getBalance() - amount);
        accountRepo.save(senderAccount);

        receiverAccount.setBalance(receiverAccount.getBalance() + amount);
        accountRepo.save(receiverAccount);

        Transaction transaction = Transaction.builder()
                .transactionTime(LocalDateTime.now())
                .amount(amount)
                .sender(senderUser)
                .senderAccount(senderAccount)
                .receiver(receiverUser)
                .receiverAccount(receiverAccount)
                .type(transactionDto.getType())
                .build();
        transactionRepository.save(transaction);
        return transaction;
    }
}
