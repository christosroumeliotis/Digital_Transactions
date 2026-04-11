package com.Bank.DigitalBankSystem.service;

import com.Bank.DigitalBankSystem.dto.UserDTO;
import com.Bank.DigitalBankSystem.entity.User;
import com.Bank.DigitalBankSystem.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserDTO addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        User userSaved = userRepo.save(user);
        return UserDTO.builder()
                .username(user.getUsername())
                .email(userSaved.getEmail()).build();
    }

    public Optional<User> findUser(Long userId) {
        return userRepo.findById(userId);
    }
}
