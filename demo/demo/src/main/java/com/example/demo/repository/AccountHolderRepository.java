package com.example.demo.repository;

import com.example.demo.model.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {

    AccountHolder findByBsnNumber(String bsnNumber);
}
