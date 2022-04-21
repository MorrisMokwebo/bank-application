package com.mainstream.banking.controller;

import com.mainstream.banking.model.*;
import com.mainstream.banking.service.AccountService;
import com.mainstream.banking.service.TransactionService;
import com.mainstream.banking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("bank-api/account")
public class AccountController {


    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @RequestMapping("/savingsAccount")
    public String savingsAccount(Model model, Principal principal) {
        List<SavingsAccountTransaction> savingsAccountTransactionHistory= transactionService.FindSavingsAccountTransactionHistory(principal.getName());


        User user = userService.findByUsername(principal.getName());
        SavingsAccount savingsAccount = user.getSavingsAccount();

        model.addAttribute("savingsAccount", savingsAccount);
        model.addAttribute("savingsAccountTransactionHistory", savingsAccountTransactionHistory);

        return "savingsAccount";
    }

    @RequestMapping("/currentAccount")
    public String currentAccount(Model model, Principal principal) {
        List<CurrentAccountTransaction> currentAccountTransactionHistory = transactionService.findCurrentAccountTransactionHistory(principal.getName());
        User user = userService.findByUsername(principal.getName());
        CurrentAccount currentAccount = user.getCurrentAccount();

        model.addAttribute("currentAccount", currentAccount);
        model.addAttribute("currentAccountTransactionHistory", currentAccountTransactionHistory);

        return "savingsAccount";
    }


    @GetMapping(value = "/deposit")
    public String deposit(Model model) {
        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");

        return "deposit";
    }

    @PostMapping(value = "/deposit")
    public String depositPOST(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal) {
        accountService.deposit(accountType, Double.parseDouble(amount), principal);

        return "redirect:/home";
    }

    @GetMapping(value = "/withdraw")
    public String withdraw(Model model) {
        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");

        return "withdraw";
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public String withdrawPOST(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal) {
        accountService.withdraw(accountType, Double.parseDouble(amount), principal);

        return "redirect:/home";
    }
}
