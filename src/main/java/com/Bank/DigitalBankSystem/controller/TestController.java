package com.Bank.DigitalBankSystem.controller;

import com.Bank.DigitalBankSystem.dto.TransactionDTO;
import com.Bank.DigitalBankSystem.enum_.TransactionTypeEnum;
import com.Bank.DigitalBankSystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    ApplicationContext applicationContext;

    @PostMapping("testing")
    public void OptimisticLockingTesting(){

        TransactionService t = applicationContext.getBean(TransactionService.class);
        Runnable task = () -> {

            for(int i=0; i<10; i++){
                System.out.println(Thread.currentThread().getName() + " entered method");
                try {
                    Thread.sleep(2000); // simulate work
                    t.depositWithdraw(TransactionDTO.builder().senderAccountId(1L)
                            .senderId(1L)
                            .type(TransactionTypeEnum.DEPOSIT)
                            .amount(1000.0)
                            .build());
                    System.out.println(Thread.currentThread().getName() + " leaving method");
                    return;
                } catch (Exception e) {
                    System.out.println("Retry: " + i);
                }
            }
        };

        Thread user1 = new Thread(task, "User-1");
        Thread user2 = new Thread(task, "User-2");

        user1.start();
        user2.start();

    }
}
