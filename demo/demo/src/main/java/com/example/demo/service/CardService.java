package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.model.Card;
import com.example.demo.model.CardType;
import com.example.demo.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CardService {

    @Autowired
    CardRepository cardRepository;

    public Iterable<Card> findAll() {
        return cardRepository.findAll();
    }

    public int getCardBalance(Long id) {
        return cardRepository.findById(id).get().getBalance();
//        return Optional.of(card.getBalance());

    }

    public Card newCard(Card card) {
        return cardRepository.save(card);
    }

    public String depositMoneyOnCard(int amount, long id) {
        if (!cardRepository.existsById(id)) {
            return null;
        }
        Card oldCard = cardRepository.findById(id).get();

        String message = depositMoney(amount, oldCard);


        return message;
    }

    public String withdrawMoneyFromCard(int amount, long id) {
        if (!cardRepository.existsById(id)) {
            return null;
        }

        Card oldCard = cardRepository.findById(id).get();


        return withdrawMoney(oldCard, amount);
    }

    private String withdrawMoney(Card card, int amount){


        Account account = card.getAccount();
        CardType cardType = card.getCardType();

        String message;
        int balance = account.getAccountBalance();
        if (balance <= 0) {
            message = "You cannot withdraw money when you have 0 balance.";
            return message;
        }
        if (amount > 0) {
            if (balance - amount >= 0) {
                if (cardType == CardType.CREDIT_CARD) {
                    message = withdrawCreditcard(amount, balance, account);
                } else {
                    balance = balance - amount;
                    message = "You withdrew " + centsToEuros(amount) + " Balance is now " + centsToEuros(balance) + ".";
                    account.setAccountBalance(balance);
                }

            } else {
                message = "There is not enough balance in your account to make this withdrawal";
            }
        } else {
            message = "Withdrawal amount has to be greater than 0";
        }
        return message;
    }
    private String withdrawCreditcard(int amount, int balance, Account account) {

        double onePercent = (double) amount / 100;
        String message;
        if ((int) (balance - onePercent) - amount >= 0) {
            balance = (int) (balance - onePercent) - amount;
            message = "You withdrew " + centsToEuros(amount) + " Balance is now " + centsToEuros(balance) +
                    ". You were charged " + onePercent + " extra for use of a credit card.";
        } else {
            message = "There is not enough balance in your account to make this withdrawal";
        }
        account.setAccountBalance(balance);
        return message;
    }

    public String depositMoney(int amount, Card card) {
        Account account = card.getAccount();
        CardType cardType = card.getCardType();
        int balance = account.getAccountBalance();
        String message;
        double onePercent = (double) amount / 100;

        if (amount > 0) {
            if (cardType == CardType.CREDIT_CARD) {
                balance = (int) (balance - onePercent);
            }
            balance = balance + amount;
            message = "You deposited " + centsToEuros(amount) + " Balance is now " + centsToEuros(balance) + ".";
            account.setAccountBalance(balance);

        } else {
            message = "Deposit amount has to be greater than 0";

        }
        System.out.println(message);
        return message;
    }

    private String centsToEuros(int cents) {
        StringBuilder centsString = new StringBuilder(String.valueOf(cents));
        if (centsString.length() < 3) {
            if (centsString.length() < 2) {
                centsString.insert(0, "0");
            }
            centsString.insert(0, "0.");
        } else {
            centsString.insert(centsString.length() - 2, '.');
        }
        return centsString.toString();
    }
}
