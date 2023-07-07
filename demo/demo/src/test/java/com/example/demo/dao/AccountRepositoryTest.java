package com.example.demo.dao;

import com.example.demo.model.Account;
import com.example.demo.model.AccountHolder;
import com.example.demo.repository.AccountHolderRepository;
import com.example.demo.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    static AccountHolder accountHolder;

    @BeforeEach
    void preloadData() {

        accountHolder = DataUtil.createAccountHolder();
        accountHolder = accountHolderRepository.save(accountHolder);

    }

    @Test
    void testCrud() {

        Account account = new Account();
        account.setAccountHolder( accountHolder );
        account.setAccountNo("12345678");
        account.setAccountBalance(0);

        var persistedAccount = accountRepository.save(account);

        assertNotNull(persistedAccount.getId());

    }




}
