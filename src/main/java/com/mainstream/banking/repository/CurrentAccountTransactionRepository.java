package com.mainstream.banking.repository;

import com.mainstream.banking.model.CurrentAccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrentAccountTransactionRepository extends JpaRepository<CurrentAccountTransaction,Long> {
    List<CurrentAccountTransaction> findAll();
}
