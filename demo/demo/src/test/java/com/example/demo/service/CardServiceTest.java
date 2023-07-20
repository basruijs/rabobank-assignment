package com.example.demo.service;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
public class CardServiceTest {
    @Autowired
    CardRepository cardRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    CardService cardService;
    Card debitCard;
    Account account1;
    Card creditCard;
    Account account2;

    public AccountHolder createAccountHolder(String bsnNumber) {
        AccountHolder ac = new AccountHolder();
        ac.setFirstName("James");
        ac.setLastName("Dean");
        ac.setBsnNumber(bsnNumber);

        return ac;
    }


    public Account createAccount(AccountHolder accountHolder, String accountNo) {
        Account ac = new Account();
        ac.setId(10L);
        ac.setAccountNo(accountNo);
        ac.setAccountHolder(accountHolder);
        ac.setAccountBalance(0);


        return ac;
    }

    public Card createCard(Account ac, CardType cardType, long id) {
        Card card = new Card();
        card.setAccount(ac);
        card.setCardType(cardType);
        card.setId(id);

        return card;
    }

    @BeforeEach
    void preloadData() {
        AccountHolder accountHolder1 = createAccountHolder("1");
        accountHolderRepository.save(accountHolder1);
        account1 = createAccount(accountHolder1, "2");
        account1 = accountRepository.save(account1);
        debitCard = createCard(account1, CardType.DEBIT_CARD, 1);
        debitCard = cardRepository.save(debitCard);

        AccountHolder accountHolder2 = createAccountHolder("2");
        accountHolderRepository.save(accountHolder2);
        account2 = createAccount(accountHolder2, "1");
        account2 = accountRepository.save(account2);
        creditCard = createCard(account2, CardType.CREDIT_CARD, 2);
        creditCard = cardRepository.save(creditCard);
    }


    @Test
    void testDebitDeposit() {
        debitCard.getAccount().setAccountBalance(0);
        cardService.depositMoneyOnCard(1000, 1);
        assertEquals(1000, debitCard.getBalance());
    }

    @Test
    void testDebitWithdraw() {
        debitCard.getAccount().setAccountBalance(1000);
        cardService.withdrawMoneyFromCard(500, 1);
        assertEquals(500, debitCard.getBalance());
    }

    @Test
    void testCreditDeposit() {
        creditCard.getAccount().setAccountBalance(0);
        cardService.depositMoneyOnCard(1000, 2);
        assertEquals(990, creditCard.getBalance());
    }

    @Test
    void testCreditWithdraw() {
        creditCard.getAccount().setAccountBalance(1000);
        cardService.withdrawMoneyFromCard(100, 1);
        assertEquals(1000, creditCard.getBalance());
    }

    @Test
    void testWithdrawWontMakeNegativeBalance() {
        debitCard.getAccount().setAccountBalance(10);
        assertEquals("There is not enough balance in your account to make this withdrawal",
                cardService.withdrawMoneyFromCard(500, 1));
    }

    @Test
    void testWithdrawAmountCannotBeNegative() {
        debitCard.getAccount().setAccountBalance(1000);
        assertEquals("Withdrawal amount has to be greater than 0",
                cardService.withdrawMoneyFromCard(-500, 1));
    }

    @Test
    void testDepositAmountCannotBeNegative() {
        debitCard.getAccount().setAccountBalance(1000);
        assertEquals("Deposit amount has to be greater than 0",
                cardService.depositMoneyOnCard(-500, 1));
    }

    @Test
    void testCreditWithdrawWontMakeNegativeBalance() {
        creditCard.getAccount().setAccountBalance(100);
        assertEquals("There is not enough balance in your account to make this withdrawal",
                cardService.withdrawMoneyFromCard(100, 2));
    }
}
