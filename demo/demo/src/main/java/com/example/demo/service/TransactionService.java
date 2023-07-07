package com.example.demo.service;

import com.example.demo.exception.ValidationException;
import com.example.demo.model.AccountHolder;

public interface TransactionService {

    void createAccountHolder(String name, String surname, String bsnNumber) throws ValidationException;

    AccountHolder getAccountHolderByBsnNumber(String bsnNumber);

    void createAccountForAccountHolder(AccountHolder accountHolder);

}
