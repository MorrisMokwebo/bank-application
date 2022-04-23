package com.mainstream.banking.service;

import com.mainstream.banking.model.*;
import com.mainstream.banking.repository.CurrentAccountRepository;
import com.mainstream.banking.repository.SavingsAccountRepository;
import com.mainstream.banking.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;

@Service
public class AccountService {
    private static int nextAccountNumber = 11223145;
    private static final Logger LOG = LoggerFactory.getLogger(AccountService.class);
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
        primaryAccount.setAccountStatus(Status.INACTIVE);


        savingsAccountRepository.save(primaryAccount);
        return savingsAccountRepository.findByAccountNumber(primaryAccount.getAccountNumber());
    }

    public CurrentAccount createCurrentAccount() {

        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setAccountBalance(new BigDecimal(100000.0));
        currentAccount.setAccountNumber(accountGen());
        currentAccount.setAccountStatus(Status.ACTIVE);

        currentAccountRepository.save(currentAccount);

        return currentAccountRepository.findByAccountNumber(currentAccount.getAccountNumber());
    }

    public void deposit(String accountType, double amount, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());

        if (accountType.equalsIgnoreCase("Savings")) {


            if(amount < 1000){
                LOG.info("To activate account please deposit R1000 or more ", user.getUsername());
            }else{
                SavingsAccount savingsAccount = user.getSavingsAccount();
                savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
                savingsAccount.setAccountStatus(Status.ACTIVE);
                savingsAccountRepository.save(savingsAccount);

                Date date = new Date();

                SavingsAccountTransaction savingsAccountTransaction = new SavingsAccountTransaction(date, "Deposit to Savings Account", "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
                transactionService.saveSavingsDepositTransaction(savingsAccountTransaction);
            }

        } else if (accountType.equalsIgnoreCase("Current")) {
            CurrentAccount currentAccount = user.getCurrentAccount();
            currentAccount.setAccountBalance(currentAccount.getAccountBalance().add(new BigDecimal(amount)));
            currentAccountRepository.save(currentAccount);

            Date date = new Date();
            CurrentAccountTransaction currentAccountTransaction = new CurrentAccountTransaction(date, "Deposit to Current Account", "Account", "Finished", amount, currentAccount.getAccountBalance(), currentAccount);
            transactionService.saveCurrentDepositTransaction(currentAccountTransaction);
        }
    }

    public void withdraw(String accountType, double amount, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());

        final BigDecimal checkAmountAgainst =  new BigDecimal("1000.00");

        if (accountType.equalsIgnoreCase("Savings")) {
            SavingsAccount savingsAccount = user.getSavingsAccount();
            //check if the account balance is greater than 1000
            //savingsAccount.getAccountBalance().compareTo(checkAmountAgainst) == 1

            if(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)).compareTo(checkAmountAgainst) == -1){
                LOG.info("Cannot Proceed with the with the  withdrawal as funds are less than R1000");
            }else{
                savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
                savingsAccountRepository.save(savingsAccount);
                Date date = new Date();

                SavingsAccountTransaction savingsAccountTransaction = new SavingsAccountTransaction(date, "Withdraw from Savings Account", "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
                transactionService.saveSavingsWithdrawTransaction(savingsAccountTransaction);
            }

        } else if (accountType.equalsIgnoreCase("Current")) {
            CurrentAccount currentAccount = user.getCurrentAccount();

            /*
            * if current balance subtract the with drawal amount is less than 0.0, transaction will not be processed
            * as this will leave the account is 0.0
            * */
            if(currentAccount.getAccountBalance().subtract(new BigDecimal(amount)).compareTo(new BigDecimal(0.00)) == -1){
                LOG.info("Cannot withdraw more than the (Current Balance + Overdraft) of the account.");
            }else{
                currentAccount.setAccountBalance(currentAccount.getAccountBalance().subtract(new BigDecimal(amount)));
                currentAccountRepository.save(currentAccount);

                Date date = new Date();
                CurrentAccountTransaction currentAccountTransaction = new CurrentAccountTransaction(date, "Withdraw from Current Account", "Account", "Finished", amount, currentAccount.getAccountBalance(), currentAccount);
                transactionService.saveCurrentWithdrawTransaction(currentAccountTransaction);
            }

        }
    }

    private int accountGen() {
        return ++nextAccountNumber;
    }
//    private boolean checkIfSavingsHasEnoughFunds(SavingsAccount savingsAccount){
//        final BigDecimal amount =  new BigDecimal("1000.00");
//
//        if(savingsAccount.getAccountBalance().compareTo(amount) == 1){
//
//        }
//    }
}
