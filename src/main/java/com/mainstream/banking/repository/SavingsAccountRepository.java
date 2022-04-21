package com.mainstream.banking.repository;

import com.mainstream.banking.model.SavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsAccountRepository extends JpaRepository<SavingsAccount,Long> {
    SavingsAccount  findByAccountNumber (int accountNumber);
}
