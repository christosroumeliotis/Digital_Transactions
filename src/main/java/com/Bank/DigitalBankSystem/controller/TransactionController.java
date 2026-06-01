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
            description = "Make a transaction (Withdraw - Deposit - Send Money)"
    )
    @ApiResponse( responseCode = "200", description = "Successful response", content =
            @Content(  mediaType = "application/json", examples =
            @ExampleObject( value = """
                                    {"response": "eleni added 1000.0 to GR123 account","timestampOfCall": "2026-06-01T08:35:14.783584Z"}
                                    """
            )
    ))
    @ApiResponse( responseCode = "200", description = "Successful response", content =
            @Content(  mediaType = "application/json", examples =
            @ExampleObject( value = """
                                    {"response": "eleni got -1000.0 from GR123 account","timestampOfCall": "2026-06-01T08:36:50.485129600Z"}
                                    """
            )
    ))
    @ApiResponse( responseCode = "200", description = "Successful response", content =
            @Content(  mediaType = "application/json", examples =
            @ExampleObject( value = """
                                    {"response": "eleni sent 1000.0 to chris","timestampOfCall": "2026-06-01T08:41:58.542191300Z"}
                                    """
            )
    ))
    @PostMapping
    public ResponseEntity<SuccessResponse<String>> makeTransaction(
            @io.swagger.v3.oas.annotations.parameters.RequestBody( description = "Transaction request", required = true, content =
            @Content( mediaType = "application/json",
            examples ={@ExampleObject( name = "Deposit Money",
                                       value = """
                                               {"amount":1000.0,"type":"DEPOSIT","senderCustomerNumber":"123","senderAccountNumber":"GR123"}
                                               """
                       ),
                       @ExampleObject( name = "Withdraw Money",
                                       value = """
                                               {"amount":1000.0,"type":"WITHDRAW","senderCustomerNumber":"123","senderAccountNumber":"GR123"}
                                               """
                       ),
                       @ExampleObject( name = "Send Money",
                                       value = """
                                               {"amount":1000.0,"type":"SENDMONEY","senderCustomerNumber":"123","senderAccountNumber":"GR123","receiverCustomerNumber":"1234","receiverAccountNumber":"GR1234"}
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
                            + transactionCreated.getSenderAccount().getAccountNumber() + " account", HttpStatus.OK);
                }
                case TransactionTypeEnum.WITHDRAW -> {
                    transactionCreated = transactionService.depositWithdraw(transaction);
                    yield utils.createSuccessResponse(transactionCreated.getSender().getUsername() + " got "
                            + transactionCreated.getAmount() + " from "
                            + transactionCreated.getSenderAccount().getAccountNumber() + " account", HttpStatus.OK);
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
