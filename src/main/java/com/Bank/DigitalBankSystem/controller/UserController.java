package com.Bank.DigitalBankSystem.controller;

import com.Bank.DigitalBankSystem.dto.UserDTO;
import com.Bank.DigitalBankSystem.dto.UserLoginDTO;
import com.Bank.DigitalBankSystem.entity.User;
import com.Bank.DigitalBankSystem.service.JwtService;
import com.Bank.DigitalBankSystem.service.UserService;
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

    @PostMapping("register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody User user){
        UserDTO userDTO = userService.addUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userDTO);
    }

    @PostMapping("login")
    public ResponseEntity<String> registerUser(@RequestBody UserLoginDTO user) {

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
