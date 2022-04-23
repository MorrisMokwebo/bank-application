package com.mainstream.banking.controller;

import com.mainstream.banking.model.User;
import com.mainstream.banking.model.security.UserRole;
import com.mainstream.banking.repository.RoleRepository;
import com.mainstream.banking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String register(@ModelAttribute("user") User user, Model model) {
        userService.createUser(user);
        return "redirect:/";
    }
}
