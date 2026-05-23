package com.Bank.DigitalBankSystem.controller;

import com.Bank.DigitalBankSystem.dto.ResponsesDto.SuccessResponse;
import com.Bank.DigitalBankSystem.dto.TransactionDTO;
import com.Bank.DigitalBankSystem.entity.Transaction;
import com.Bank.DigitalBankSystem.enum_.TransactionTypeEnum;
import com.Bank.DigitalBankSystem.service.TransactionService;
import com.Bank.DigitalBankSystem.utils.interfaces.Utils;
import com.Bank.DigitalBankSystem.utils.interfacesImpl.UtilsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "Make a transaction",
            description = "Make a transaction Withdraw - Deposit - Send Money to other account"
    )
    @ApiResponse( responseCode = "200", description = "Successful response", content =
            @Content(  mediaType = "application/json", examples =
            @ExampleObject( value = """
                                    { "response": "eleni added 1000.0 to 3 account", "timestampOfCall": "2026-05-23T12:33:29.990665700Z" }
                                    """
            )
    ))
    @ApiResponse( responseCode = "200", description = "Successful response", content =
            @Content(  mediaType = "application/json", examples =
            @ExampleObject( value = """
                                    { "response": "eleni got -1000.0 from 3 account", "timestampOfCall": "2026-05-23T12:34:25.441902400Z" }
                                    """
            )
    ))
    @ApiResponse( responseCode = "200", description = "Successful response", content =
            @Content(  mediaType = "application/json", examples =
            @ExampleObject( value = """
                                    { "response": "chris sent 1000.0 to eleni", "timestampOfCall": "2026-05-23T12:35:37.427445900Z" }  
                                    """
            )
    ))
    @PostMapping
    public ResponseEntity<SuccessResponse<String>> makeTransaction(
            @io.swagger.v3.oas.annotations.parameters.RequestBody( description = "Transaction request", required = true, content =
            @Content( mediaType = "application/json",
            examples ={@ExampleObject( name = "Deposit Money",
                                       value = """
                                               { "amount":1000.0, "type":"DEPOSIT", "senderId":2, "senderAccountId":3 }
                                               """
                       ),
                       @ExampleObject( name = "Withdraw Money",
                                       value = """
                                               { "amount":1000.0, "type":"WITHDRAW", "senderId":2, "senderAccountId":3 }
                                               """
                       ),
                       @ExampleObject( name = "Send Money",
                                       value = """
                                               { "amount":1000.0, "type":"SENDMONEY", "senderId":1, "senderAccountId":1, "receiverId":"2", "receiverAccountId":"3" }
                                               """
                       )}

            ))
            @Valid @RequestBody TransactionDTO transaction) throws Exception {

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
