package com.example.demo.service;

import com.example.demo.exception.ValidationException;
import com.example.demo.model.AccountHolder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TransactionServiceImplTest {

    @Autowired
    TransactionService transactionService;

    @Test
    void testAccountHolder() throws ValidationException {

        transactionService.createAccountHolder("James", "Dean", "ABC123");

        AccountHolder accountHolder = transactionService.getAccountHolderByBsnNumber("ABC123");

        assertNotNull(accountHolder);
    }

}
