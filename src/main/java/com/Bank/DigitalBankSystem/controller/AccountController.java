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
                            {"response": "eleni created a new account!", "timestampOfCall": "2026-05-23T12:20:25.945696700Z"}
                            """
            )
    ))
    @PostMapping("/user/{userId}")
    public ResponseEntity<SuccessResponse<String>> createAccount(
            @Parameter( description = "User ID", required = true, example = "2")
            @PathVariable Long userId) throws Exception {

        return utils.createSuccessResponse(accountService.createAccount(userId), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Accounts",
            description = "Get the accounts of a User"
    )
    @ApiResponse( responseCode = "200", description = "Successful response", content =
            @Content(  mediaType = "application/json", examples =
            @ExampleObject( value = """
                            {"response": [ { "balance": 128500.0, "createdAt": "2026-04-11T13:09:22.251743" }, { "balance": 0.0, "createdAt": "2026-04-11T13:09:33.125871" } ], "timestampOfCall": "2026-05-23T12:25:37.768822500Z" }
                            """
            )
    ))
    @GetMapping("/user/{userId}")
    public ResponseEntity<SuccessResponse<List<AccountDTO>>> getUserAccounts(
            @Parameter( description = "User ID", required = true, example = "1")
            @PathVariable Long userId) throws Exception {

          List<Account> accounts = accountService.findAccountsByUserId(userId);
          List<AccountDTO> accountsReturn = new ArrayList<>();
          for(Account account : accounts){
              accountsReturn.add(AccountMapper.INSTANCE.toDto(account));
          }
          return utils.createSuccessResponse(accountsReturn, HttpStatus.FOUND);
    }

    @Operation(
            summary = "Get Account",
            description = "Get an account using its ID"
    )
    @ApiResponse( responseCode = "200", description = "Successful response", content =
            @Content(  mediaType = "application/json", examples =
            @ExampleObject( value = """
                            {"response": [ { "balance": 128500.0, "createdAt": "2026-04-11T13:09:22.251743" }, { "balance": 0.0, "createdAt": "2026-04-11T13:09:33.125871" } ], "timestampOfCall": "2026-05-23T12:25:37.768822500Z" }
                            """
            )
    ))
    @GetMapping("/{accountId}")
    public ResponseEntity<SuccessResponse<AccountDTO>> getAccountById(
            @Parameter( description = "Account ID", required = true, example = "1")
            @PathVariable Long accountId){
        Account account = accountService.findAccountByAccountId(accountId);
        return utils.createSuccessResponse(AccountMapper.INSTANCE.toDto(account), HttpStatus.FOUND);
    }
}
