package com.example.demo.dao;

import com.example.demo.model.AccountHolder;
import com.example.demo.repository.AccountHolderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class AccountHolderRepositoryTest {

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Test
    void testCrudMethods() {

       AccountHolder accountHolder = DataUtil.createAccountHolder();

        var persistedAC = accountHolderRepository.save(accountHolder);
        assertNotNull(persistedAC.getId());
        
        var byBsn = accountHolderRepository.findByBsnNumber("1234XYZ");
        assertNotNull(byBsn.getId());



    }

}
