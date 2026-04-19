package com.Bank.DigitalBankSystem.controller;

import com.Bank.DigitalBankSystem.dto.ResponsesDto.SuccessResponse;
import com.Bank.DigitalBankSystem.dto.TransactionDTO;
import com.Bank.DigitalBankSystem.entity.Transaction;
import com.Bank.DigitalBankSystem.enum_.TransactionTypeEnum;
import com.Bank.DigitalBankSystem.service.TransactionService;
import com.Bank.DigitalBankSystem.utils.interfaces.Utils;
import com.Bank.DigitalBankSystem.utils.interfacesImpl.UtilsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    private final Utils utils = new UtilsImpl();

    @PostMapping
    public ResponseEntity<SuccessResponse<String>> makeTransaction(@Valid @RequestBody TransactionDTO transaction) throws Exception {

//        try{
//            TransactionTypeEnum transactionType = transaction.getType();
//            Transaction transactionCreated;
//            return switch (transactionType) {
//                case TransactionTypeEnum.DEPOSIT -> {
//                    transactionCreated = transactionService.depositWithdraw(transaction);
//                    yield ResponseEntity.ok(transactionCreated.getSender().getUsername() + " added "
//                            + transactionCreated.getAmount() + " to "
//                            + transactionCreated.getSenderAccount().getId() + " account");
//                }
//                case TransactionTypeEnum.WITHDRAW -> {
//                    transactionCreated = transactionService.depositWithdraw(transaction);
//                    yield ResponseEntity.ok(transactionCreated.getSender().getUsername() + " got "
//                            + transactionCreated.getAmount() + " from "
//                            + transactionCreated.getSenderAccount().getId() + " account");
//                }
//                case TransactionTypeEnum.SENDMONEY -> {
//                    transactionCreated = transactionService.sendMoney(transaction);
//                    yield ResponseEntity.ok(transactionCreated.getSender().getUsername() + " sent "
//                            + transactionCreated.getAmount() + " to "
//                            + transactionCreated.getReceiver().getUsername());
//                }
//            };
//        }catch (Exception exception){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
//        }
            TransactionTypeEnum transactionType = transaction.getType();
            Transaction transactionCreated;
            return switch (transactionType) {
                case TransactionTypeEnum.DEPOSIT -> {
                    transactionCreated = transactionService.depositWithdraw(transaction);
                    yield utils.createSuccessResponse(transactionCreated.getSender().getUsername() + " added "
                            + transactionCreated.getAmount() + " to "
                            + transactionCreated.getSenderAccount().getId() + " account", HttpStatus.OK);
                }
                case TransactionTypeEnum.WITHDRAW -> {
                    transactionCreated = transactionService.depositWithdraw(transaction);
                    yield utils.createSuccessResponse(transactionCreated.getSender().getUsername() + " got "
                            + transactionCreated.getAmount() + " from "
                            + transactionCreated.getSenderAccount().getId() + " account", HttpStatus.OK);
                }
                case TransactionTypeEnum.SENDMONEY -> {
                    transactionCreated = transactionService.sendMoney(transaction);
                    yield utils.createSuccessResponse(transactionCreated.getSender().getUsername() + " sent "
                            + transactionCreated.getAmount() + " to "
                            + transactionCreated.getReceiver().getUsername(), HttpStatus.OK);
                }
            };
    }
}
