package com.mainstream.banking.repository;

import com.mainstream.banking.model.CurrentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentAccountRepository extends JpaRepository<CurrentAccount,Long> {
    CurrentAccount findByAccountNumber (int accountNumber);
}

