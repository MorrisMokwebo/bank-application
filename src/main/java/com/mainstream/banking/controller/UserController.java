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

import java.util.HashSet;
import java.util.Set;

@Controller
//@RequestMapping("/bank-api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String register(@ModelAttribute("user") User user, Model model) {

        //Set<UserRole> userRoles = new HashSet<>();
       // userRoles.add(new UserRole(user, roleRepository.findByName("ROLE_USER")));

        userService.createUser(user);

        return "redirect:/";
    /*    if(userService.checkUserExists(user.getUsername(), user.getEmail()))  {

            if (userService.checkEmailExists(user.getEmail())) {
                model.addAttribute("emailExists", true);
            }

            if (userService.checkUsernameExists(user.getUsername())) {
                model.addAttribute("usernameExists", true);
            }

            return "signup";
        } else {

        }*/
    }
}
