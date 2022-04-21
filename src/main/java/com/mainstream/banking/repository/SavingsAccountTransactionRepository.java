package com.mainstream.banking.repository;

import com.mainstream.banking.model.SavingsAccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SavingsAccountTransactionRepository extends JpaRepository<SavingsAccountTransaction,Long> {
    List<SavingsAccountTransaction> findAll();
}
