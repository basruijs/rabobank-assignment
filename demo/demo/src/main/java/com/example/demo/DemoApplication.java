package com.example.demo;

import com.example.demo.model.Card;
import com.example.demo.model.Account;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
//		Card card = new Card(124L, "30/06", true, 1000);
//		Account user = new Account("Bas", "Ruijs");
//		Card card2 = new Card(123L, "30/06", true, 1000);
//		card.withdrawMoney(1000);
//		card.depositMoney(1);
//		card.depositMoney(1000000000);
//		System.out.println(card.getCardNr());

	}

}
