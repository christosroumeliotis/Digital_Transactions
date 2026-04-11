package com.Bank.DigitalBankSystem.service;

import com.Bank.DigitalBankSystem.entity.User;
import com.Bank.DigitalBankSystem.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User with username" + username + "not found!");
        }

        return user;
    }
}
