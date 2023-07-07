package com.example.demo.dao;

import com.example.demo.model.Account;
import com.example.demo.model.AccountHolder;
import com.example.demo.model.Card;
import com.example.demo.model.CardType;
import com.example.demo.repository.AccountHolderRepository;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class CardRepositoryTest {
    @Autowired
    CardRepository cardRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    static AccountHolder accountHolder;

    static Account account;

    @BeforeEach
    void preloadData() {
        accountHolder = DataUtil.createAccountHolder();
        accountHolder = accountHolderRepository.save(accountHolder);

        account = DataUtil.createAccount();
        account = accountRepository.save(account);

    }

    @Test
    void testCrud() {

        Card card = new Card();
        card.setCardType(CardType.DEBIT_CARD);
        card.setId(100L);
        card.setAccount(account);

        Card persistedCard = cardRepository.save(card);

        assertNotNull(persistedCard.getId());

    }

}
