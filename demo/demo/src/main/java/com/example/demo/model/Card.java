package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.bridge.Message;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Card {
    @Id
    private Long cardNr;
    private String expirationDate;
    private boolean isCreditCard;
    //Balance in cents, so there won't be any rounding shenanigans with doubles
    private int balance;



    public String withdrawMoney(int amount){
        String message;
        if(balance <= 0){
            message="You cannot withdraw money when you have 0 balance.";
            return message;
        }
        if(amount>0) {
            double onePercent = (double) amount / 100;

            if (!isCreditCard || balance - amount - onePercent >= 0) {
                if (balance - amount < 0) {
                    amount = balance;
                }
                balance = balance - amount;
                if(isCreditCard){
                    balance = (int) (balance - onePercent);
                }
                message = "You withdrew " + centsToEuros(amount) + " Balance is now " + centsToEuros(balance) + ".";

            } else {
                message = "There is not enough money to charge the additional 1% for use of creditcard in your account";
            }
        } else {
            message = "Withdrawal amount has to be greater than 0";
        }
        return message;
    }

    public String depositMoney(int amount){
        String message;
        double onePercent = (double) amount / 100;

        if(amount>0){
            if(isCreditCard){
                balance = (int) (balance - onePercent);
            }
            balance = balance + amount;
            message = "You deposited " + centsToEuros(amount) + " Balance is now " + centsToEuros(balance) + ".";

        } else {
            message = "Deposit amount has to be greater than 0";

        }
        System.out.println(message);
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
}
