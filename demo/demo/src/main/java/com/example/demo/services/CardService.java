package com.example.demo.services;

import com.example.demo.model.Card;
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

    public int getCardBalance(Long cardNr) {
        return cardRepository.findByCardNr(cardNr).get().getBalance();
//        return Optional.of(card.getBalance());

    }

    public Card newCard(Card card) {
        return cardRepository.save(card);
    }

    public String depositMoneyOnCard(int amount, long id) {
        if (!cardRepository.existsById(id)) {
            return null;
        }
        Card oldCard = cardRepository.findByCardNr(id).get();

        String message = oldCard.depositMoney(amount);


        return message;
    }

    public String withdrawMoneyFromCard(int amount, long id) {
        if (!cardRepository.existsById(id)) {
            return null;
        }
        Card oldCard = cardRepository.findByCardNr(id).get();

        String message = oldCard.withdrawMoney(amount);


        return message;
    }
}
