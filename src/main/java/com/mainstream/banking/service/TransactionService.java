package com.mainstream.banking.service;

import com.mainstream.banking.model.*;
import com.mainstream.banking.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private CurrentAccountRepository currentAccountRepository;

    @Autowired
    private SavingsAccountTransactionRepository savingsAccountTransactionRepository;

    @Autowired
    private CurrentAccountTransactionRepository currentAccountTransactionRepository;


    public List<SavingsAccountTransaction> FindSavingsAccountTransactionHistory(String username){
        User user = userRepository.findByUsername(username);
        List<SavingsAccountTransaction> savingsAccountTransactionHistory = user.getSavingsAccount().getSavingsAccountTransactionHistory();

        return savingsAccountTransactionHistory;
    }

    public List<CurrentAccountTransaction> findCurrentAccountTransactionHistory(String username) {
        User user = userRepository.findByUsername(username);
        List<CurrentAccountTransaction> currentAccountTransactionHistory = user.getCurrentAccount().getCurrentAccountTransactionHistory();

        return currentAccountTransactionHistory;
    }

    public void saveSavingsDepositTransaction(SavingsAccountTransaction savingsAccountTransaction) {
        savingsAccountTransactionRepository.save(savingsAccountTransaction);
    }

    public void saveCurrentDepositTransaction(CurrentAccountTransaction currentAccountTransaction) {
        currentAccountTransactionRepository.save(currentAccountTransaction);
    }

    public void saveSavingsWithdrawTransaction(SavingsAccountTransaction savingsAccountTransaction) {
        savingsAccountTransactionRepository.save(savingsAccountTransaction);
    }

    public void saveCurrentWithdrawTransaction(CurrentAccountTransaction currentAccountTransaction) {
        currentAccountTransactionRepository.save(currentAccountTransaction);
    }

    public void betweenAccountsTransfer(String transferFrom, String transferTo, Double amount, SavingsAccount savingsAccount, CurrentAccount currentAccount) throws Exception {
        if (transferFrom.equalsIgnoreCase("Savings") && transferTo.equalsIgnoreCase("Current")) {
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            currentAccount.setAccountBalance(currentAccount.getAccountBalance().add(new BigDecimal(amount)));
            savingsAccountRepository.save(savingsAccount);
            currentAccountRepository.save(currentAccount);

            Date date = new Date();

            SavingsAccountTransaction savingsAccountTransaction  =
                    new SavingsAccountTransaction(date, "Between account transfer from "+transferFrom+" to "+transferTo, "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
            CurrentAccountTransaction currentAccountTransaction =
                    new CurrentAccountTransaction  (date, "Between account transfer from "+transferFrom+" to "+transferTo, "Transfer", "Finished", amount, currentAccount.getAccountBalance(), currentAccount);

            currentAccountTransactionRepository.save(currentAccountTransaction);
            savingsAccountTransactionRepository.save(savingsAccountTransaction);

        } else if (transferFrom.equalsIgnoreCase("Current") && transferTo.equalsIgnoreCase("Savings")) {
            currentAccount.setAccountBalance(currentAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
            currentAccountRepository.save(currentAccount);
            savingsAccountRepository.save(savingsAccount);


            Date date = new Date();

            CurrentAccountTransaction currentAccountTransaction =
                    new CurrentAccountTransaction  (date, "Between account transfer from "+transferFrom+" to "+transferTo, "Transfer", "Finished", amount, currentAccount.getAccountBalance(), currentAccount);
            SavingsAccountTransaction savingsAccountTransaction  =
                    new SavingsAccountTransaction(date, "Between account transfer from "+transferFrom+" to "+transferTo, "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);

            currentAccountTransactionRepository.save(currentAccountTransaction);
            savingsAccountTransactionRepository.save(savingsAccountTransaction);
        } else {
            throw new Exception("Invalid Transfer");
        }
    }

}
