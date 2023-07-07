package com.example.demo.service;

import com.example.demo.exception.ValidationException;
import com.example.demo.repository.AccountHolderRepository;
import com.example.demo.repository.AccountRepository;
import com.example.demo.model.AccountHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService{

    private final AccountHolderRepository accountHolderRepository;

    private final AccountRepository accountRepository;

    public TransactionServiceImpl(AccountHolderRepository accountHolderRepository, AccountRepository accountRepository) {
        this.accountHolderRepository = accountHolderRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void createAccountHolder(String name, String surname, String bsn)
            throws ValidationException {

        //validation
        //check that accountHolder does not exist
        if( accountHolderRepository.findByBsnNumber(bsn) != null ) {
            log.error("Account holder already exists for bsn={}, ", bsn);
            throw new ValidationException("Account holder already exists");
        }

        AccountHolder ac = new AccountHolder();
        ac.setBsnNumber(bsn);
        ac.setFirstName(name);
        ac.setLastName(surname);

        accountHolderRepository.save(ac);

    }

    @Override
    public AccountHolder getAccountHolderByBsnNumber(String bsnNumber) {
        return accountHolderRepository.findByBsnNumber(bsnNumber);
    }

    @Override
    public void createAccountForAccountHolder(AccountHolder accountHolder) {

    }
}
