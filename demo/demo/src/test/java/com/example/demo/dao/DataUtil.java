package com.example.demo.dao;

import com.example.demo.model.Account;
import com.example.demo.model.AccountHolder;
import com.example.demo.model.Card;
import com.example.demo.model.CardType;

public class DataUtil {

    public static AccountHolder createAccountHolder() {
        AccountHolder ac = new AccountHolder();
        ac.setFirstName("James");
        ac.setLastName("Dean");
        ac.setBsnNumber("1234XYZ");

        return ac;
    }

    public static Account createAccount() {
        Account ac = new Account();
        ac.setId(1L);
        ac.setAccountNo("ABC123DEF");
        ac.setAccountHolder(createAccountHolder());
        ac.setAccountBalance(0);

        return ac;
    }

    public static Card createCard() {
        Card card = new Card();
        card.setCardType(CardType.DEBIT_CARD);
        card.setId(123123123L);
        card.setAccount(createAccount());

        return card;
    }
}
