package com.Bank.DigitalBankSystem.controller;

import com.Bank.DigitalBankSystem.config.mapper.AccountMapper;
import com.Bank.DigitalBankSystem.dto.AccountDTO;
import com.Bank.DigitalBankSystem.dto.ResponsesDto.SuccessResponse;
import com.Bank.DigitalBankSystem.entity.Account;
import com.Bank.DigitalBankSystem.service.AccountService;
import com.Bank.DigitalBankSystem.utils.interfaces.Utils;
import com.Bank.DigitalBankSystem.utils.interfacesImpl.UtilsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "Create a new account",
            description = "Creates a new account for the userId"
    )
    @ApiResponse( responseCode = "200", description = "Successful response", content =
            @Content(  mediaType = "application/json", examples =
            @ExampleObject( value = """
                            {"response": "eleni created the GR@1234 account!", "timestampOfCall": "2026-05-23T12:20:25.945696700Z"}
                            """
            )
    ))
    @PostMapping("/user")
    public ResponseEntity<SuccessResponse<String>> createAccount(
            @Parameter( description = "Customer Number", required = true, example = "123")
            @RequestParam() String customerNumber,
            @Parameter( description = "Account Number", required = true, example = "GR@1234")
            @RequestParam() String accountNumber) throws Exception {

        return utils.createSuccessResponse(accountService.createAccount(customerNumber, accountNumber), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Accounts",
            description = "Get the accounts of a User"
    )
    @ApiResponse( responseCode = "200", description = "Successful response", content =
            @Content(  mediaType = "application/json", examples =
            @ExampleObject( value = """
                                    {"response": [{"accountNumber": "GR123","balance": 0.0,"createdAt": "2026-06-01T11:01:05.013237"},
                                                  {"accountNumber": "GR@123","balance": 0.0,"createdAt": "2026-06-01T11:03:13.953875"},
                                                  {"accountNumber": "GR@1234","balance": 0.0,"createdAt": "2026-06-01T11:19:56.970809"}],"timestampOfCall": "2026-06-01T08:21:46.464947800Z"}
                                    """
            )
    ))
    @GetMapping("/user")
    public ResponseEntity<SuccessResponse<List<AccountDTO>>> getUserAccounts(
            @Parameter( description = "Customer Number", required = true, example = "123")
            @RequestParam() String customerNumber) throws Exception {

          List<Account> accounts = accountService.findAccountsByCustomerNumber(customerNumber);
          List<AccountDTO> accountsReturn = new ArrayList<>();
          for(Account account : accounts){
              accountsReturn.add(AccountMapper.INSTANCE.toDto(account));
          }
          return utils.createSuccessResponse(accountsReturn, HttpStatus.FOUND);
    }

    @Operation(
            summary = "Get Account",
            description = "Get an account using Account Number"
    )
    @ApiResponse( responseCode = "200", description = "Successful response", content =
            @Content(  mediaType = "application/json", examples =
            @ExampleObject( value = """
                                    {"response": {"accountNumber": "GR123","balance": 0.0,"createdAt": "2026-06-01T11:01:05.013237"},"timestampOfCall": "2026-06-01T08:23:33.778201400Z"}
                                    """
            )
    ))
    @GetMapping("")
    public ResponseEntity<SuccessResponse<AccountDTO>> getAccountByNumber(
            @Parameter( description = "Account Number", required = true, example = "GR123")
            @RequestParam String accountNumber){
        Account account = accountService.findAccountByAccountNumber(accountNumber);
        return utils.createSuccessResponse(AccountMapper.INSTANCE.toDto(account), HttpStatus.FOUND);
    }
}
