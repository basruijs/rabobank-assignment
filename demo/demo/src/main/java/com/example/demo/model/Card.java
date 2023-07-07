package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.bridge.Message;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @OneToOne
    private Account account;



    public String withdrawMoney(int amount){
        String message;
        int balance = account.getAccountBalance();
        if(balance <= 0){
            message="You cannot withdraw money when you have 0 balance.";
            return message;
        }
        if(amount>0) {
            double onePercent = (double) amount / 100;

            if (balance - amount >= 0) {

                if(cardType == CardType.CREDIT_CARD){
                    if((int) (balance - onePercent) - amount >=0){
                        balance = (int) (balance - onePercent) - amount;
                        message = "You withdrew " + centsToEuros(amount) + " Balance is now " + centsToEuros(balance) +
                                ". You were charged " + onePercent + " extra for use of a credit card.";
                        ;
                    } else {
                        message = "There is not enough balance in your account to make this withdrawal";
                    }
                } else {
                    balance = balance - amount;
                    message = "You withdrew " + centsToEuros(amount) + " Balance is now " + centsToEuros(balance) + ".";
                }
                account.setAccountBalance(balance);


            } else {
                message = "There is not enough balance in your account to make this withdrawal";
            }
        } else {
            message = "Withdrawal amount has to be greater than 0";
        }
        return message;
    }

    public String depositMoney(int amount){
        int balance = account.getAccountBalance();
        String message;
        double onePercent = (double) amount / 100;

        if(amount>0){
            if(cardType == CardType.CREDIT_CARD){
                balance = (int) (balance - onePercent);
            }
            balance = balance + amount;
            message = "You deposited " + centsToEuros(amount) + " Balance is now " + centsToEuros(balance) + ".";
            account.setAccountBalance(balance);

        } else {
            message = "Deposit amount has to be greater than 0";

        }
        System.out.println(message);
        System.out.println(getBalance());
        return message;
    }

    private String centsToEuros(int cents){
        StringBuilder centsString = new StringBuilder(String.valueOf(cents));
        if(centsString.length()<3){
            if(centsString.length()<2){
                centsString.insert(0, "0");
            }
            centsString.insert(0, "0.");
        } else {
            centsString.insert(centsString.length() - 2, '.');
        }
        return centsString.toString();
    }

    public int getBalance(){
        return account.getAccountBalance();
    }
}
