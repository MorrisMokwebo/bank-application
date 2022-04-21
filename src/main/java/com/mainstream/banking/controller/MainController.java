package com.mainstream.banking.controller;

import com.mainstream.banking.model.CurrentAccount;
import com.mainstream.banking.model.SavingsAccount;
import com.mainstream.banking.model.User;
import com.mainstream.banking.repository.RoleRepository;
import com.mainstream.banking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;


    @RequestMapping("/")
    public String home() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        User user = new User();

        model.addAttribute("user", user);

        return "signup";
    }

    @RequestMapping("/home")
    public String userFront(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        SavingsAccount savingsAccount = user.getSavingsAccount();
        CurrentAccount currentAccount = user.getCurrentAccount();

        model.addAttribute("savingsAccount", savingsAccount);
        model.addAttribute("currentAccount", currentAccount);

        return "userFront";
    }

}
