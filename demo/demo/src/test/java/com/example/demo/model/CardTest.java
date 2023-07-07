package com.example.demo.model;

import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class CardTest {
    @Autowired
    CardRepository cardRepository;

    @Autowired
    AccountRepository accountRepository;

    Card debitCard;
    Account account1;
    Card creditCard;
    Account account2;


    public AccountHolder createAccountHolder() {
        AccountHolder ac = new AccountHolder();
        ac.setFirstName("James");
        ac.setLastName("Dean");
        ac.setBsnNumber("1234XYZ");

        return ac;
    }

    public Account createAccount() {
        Account ac = new Account();
        ac.setId(10L);
        ac.setAccountNo("ABC123DEF");
        ac.setAccountHolder(createAccountHolder());
        ac.setAccountBalance(0);


        return ac;
    }

    public Card createCard(Account ac, CardType cardType) {
        Card card = new Card();
        card.setAccount(ac);
        card.setCardType(cardType);
        card.setId(123123123L);

        return card;
    }

    @BeforeEach
    void preloadData() {
        account1 = createAccount();
        account1 = accountRepository.save(account1);
        debitCard = createCard(account1, CardType.DEBIT_CARD);
        debitCard = cardRepository.save(debitCard);

        account2 = createAccount();
        account2 = accountRepository.save(account2);
        creditCard = createCard(account2, CardType.CREDIT_CARD);
        creditCard = cardRepository.save(creditCard);
    }

    @Test
    void testDebitDeposit(){
        debitCard.getAccount().setAccountBalance(0);
        debitCard.depositMoney(1000);
        assertEquals(1000, debitCard.getBalance());
    }

    @Test
    void testDebitWithdraw(){
        debitCard.getAccount().setAccountBalance(1000);
        debitCard.withdrawMoney(500);
        assertEquals(500, debitCard.getBalance());
    }

    @Test
    void testCreditDeposit(){
        creditCard.getAccount().setAccountBalance(0);
        creditCard.depositMoney(1000);
        assertEquals(990, creditCard.getBalance());
    }

    @Test
    void testCreditWithdraw(){
        creditCard.getAccount().setAccountBalance(1000);
        creditCard.withdrawMoney(100);
        assertEquals(899, creditCard.getBalance());
    }

    @Test
    void testWithdrawWontMakeNegativeBalance(){
        debitCard.getAccount().setAccountBalance(10);
        assertEquals("There is not enough balance in your account to make this withdrawal",
                debitCard.withdrawMoney(500));
    }

    @Test
    void testWithdrawAmountCannotBeNegative(){
        debitCard.getAccount().setAccountBalance(1000);
        assertEquals("Withdrawal amount has to be greater than 0",
                debitCard.withdrawMoney(-500));
    }

    @Test
    void testDepositAmountCannotBeNegative(){
        debitCard.getAccount().setAccountBalance(1000);
        assertEquals("Deposit amount has to be greater than 0",
                debitCard.depositMoney(-500));
    }

    @Test
    void testCreditWithdrawWontMakeNegativeBalance(){
        creditCard.getAccount().setAccountBalance(100);
        assertEquals("There is not enough balance in your account to make this withdrawal",
                creditCard.withdrawMoney(100));
    }
}
