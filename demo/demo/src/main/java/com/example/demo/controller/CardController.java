package com.example.demo.controller;


import com.example.demo.model.Card;
import com.example.demo.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin
@RequestMapping("/api/card")
public class CardController {
    @Autowired
    CardService cardService;

    @PostMapping("/new")
    public Card newCard(@RequestBody Card card) {
        return cardService.newCard(card);
    }

    @GetMapping("/all")
    public Iterable<Card> getAllCards() {
        return cardService.findAll();
    }

    @GetMapping("/{id}/balance")
    public int getBalance(@PathVariable(value = "id") long id) {
        return cardService.getCardBalance(id);
    }

    @PutMapping("/{id}/deposit")
    public String deposit(@RequestBody int amount, @PathVariable(value = "id") long id) {
        return cardService.depositMoneyOnCard(amount, id);
    }
    @PutMapping("/{id}/withdraw")
    public String withdraw(@RequestBody int amount, @PathVariable(value = "id") long id) {
        return cardService.withdrawMoneyFromCard(amount, id);
    }


}
