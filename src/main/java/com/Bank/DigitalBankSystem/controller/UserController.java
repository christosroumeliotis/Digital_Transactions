package com.Bank.DigitalBankSystem.controller;

import com.Bank.DigitalBankSystem.dto.UserDTO;
import com.Bank.DigitalBankSystem.dto.UserLoginDTO;
import com.Bank.DigitalBankSystem.entity.User;
import com.Bank.DigitalBankSystem.service.JwtService;
import com.Bank.DigitalBankSystem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("hello")
    public ResponseEntity<String> registerUser(){
        return ResponseEntity.ok("Hello!");
    }


    @Operation(
            summary = "Create a new user",
            description = "Creates a new user"
    )
    @ApiResponse( responseCode = "200", description = "Successful response", content =
            @Content(  mediaType = "application/json", examples =
            @ExampleObject( value = """
                                    {"customerNumber": "123","username": "eleni","email": "eleni@gmail.com"}
                                    """
            )
    ))
    @PostMapping("register")
    public ResponseEntity<UserDTO> registerUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody( description = "Create new user request", required = true, content =
            @Content( mediaType = "application/json", examples =
            @ExampleObject( name = "Create New User Request",
                            value = """
                                    {"customerNumber": "123","username": "eleni","password": "eleni123","email":"eleni@gmail.com","role":"USER"}
                                    """
            )))
            @Valid @RequestBody User user){
        UserDTO userDTO = userService.addUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userDTO);
    }

    @Operation(
            summary = "Login a User",
            description = "Generates a new JWT Token"
    )
    @ApiResponse( responseCode = "200", description = "Successful response", content =
    @Content(  mediaType = "application/json", examples =
    @ExampleObject( name = "Create JWT - Login Request",
                    value = """ 
                            eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaHJpcyIsImlhdCI6MTc3OTUzODM4OSwiZXhwIjoxNzc5NTM4Njg5fQ.hXuR4yLwA2Zz-VqwuIc8xkfugUONUmQVnDSXa9YiTtg
                            """
    )))
    @PostMapping("login")
    public ResponseEntity<String> registerUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody( description = "Create new user request", required = true, content =
            @Content( mediaType = "application/json", examples =
            @ExampleObject( name = "Create JWT - Login Request",
                            value = """
                                    { "username": "chris", "password": "chris123" }
                                    """
            )))
            @Valid @RequestBody UserLoginDTO user) {

        //Authenticate the username and password
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword()
        ));
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(user.getUsername());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(token);
        } else {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Failed to log in!");
        }
    }
}
