package com.mainstream.banking.service;

import com.mainstream.banking.model.*;
import com.mainstream.banking.repository.CurrentAccountRepository;
import com.mainstream.banking.repository.SavingsAccountRepository;
import com.mainstream.banking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;

@Service
public class AccountService {
    private static int nextAccountNumber = 11223145;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private CurrentAccountRepository currentAccountRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private TransactionService transactionService;

    public SavingsAccount createSavingsAccount() {
        SavingsAccount primaryAccount = new SavingsAccount();
        primaryAccount.setAccountBalance(new BigDecimal(0.0));
        primaryAccount.setAccountNumber(accountGen());

        savingsAccountRepository.save(primaryAccount);
        return savingsAccountRepository.findByAccountNumber(primaryAccount.getAccountNumber());
    }

    public CurrentAccount createCurrentAccount() {
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setAccountBalance(new BigDecimal(0.0));
        currentAccount.setAccountNumber(accountGen());

        currentAccountRepository.save(currentAccount);

        return currentAccountRepository.findByAccountNumber(currentAccount.getAccountNumber());
    }

    public void deposit(String accountType, double amount, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());

        if (accountType.equalsIgnoreCase("Savings")) {
            SavingsAccount savingsAccount = user.getSavingsAccount();
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
            savingsAccountRepository.save(savingsAccount);

            Date date = new Date();

            SavingsAccountTransaction savingsAccountTransaction = new SavingsAccountTransaction(date, "Deposit to Primary Account", "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
            transactionService.saveSavingsDepositTransaction(savingsAccountTransaction);

        } else if (accountType.equalsIgnoreCase("Current")) {
            CurrentAccount currentAccount = user.getCurrentAccount();
            currentAccount.setAccountBalance(currentAccount.getAccountBalance().add(new BigDecimal(amount)));
            currentAccountRepository.save(currentAccount);

            Date date = new Date();
            CurrentAccountTransaction currentAccountTransaction = new CurrentAccountTransaction(date, "Deposit to savings Account", "Account", "Finished", amount, currentAccount.getAccountBalance(), currentAccount);
            transactionService.saveCurrentDepositTransaction(currentAccountTransaction);
        }
    }

    public void withdraw(String accountType, double amount, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());

        if (accountType.equalsIgnoreCase("Savings")) {
            SavingsAccount savingsAccount = user.getSavingsAccount();
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            savingsAccountRepository.save(savingsAccount);


            Date date = new Date();

            SavingsAccountTransaction savingsAccountTransaction = new SavingsAccountTransaction(date, "Withdraw from Primary Account", "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
            transactionService.saveSavingsWithdrawTransaction(savingsAccountTransaction);

        } else if (accountType.equalsIgnoreCase("Current")) {
            CurrentAccount currentAccount = user.getCurrentAccount();
            currentAccount.setAccountBalance(currentAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            currentAccountRepository.save(currentAccount);

            Date date = new Date();
            CurrentAccountTransaction currentAccountTransaction = new CurrentAccountTransaction(date, "Withdraw from savings Account", "Account", "Finished", amount, currentAccount.getAccountBalance(), currentAccount);
            transactionService.saveCurrentWithdrawTransaction(currentAccountTransaction);
        }
    }

    private int accountGen() {
        return ++nextAccountNumber;
    }
}
