package com.mainstream.banking.service;


import com.mainstream.banking.model.User;
import com.mainstream.banking.model.security.UserRole;
import com.mainstream.banking.repository.RoleRepository;
import com.mainstream.banking.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;


@Service
@Transactional
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AccountService accountService;


    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public User createUser(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser != null) {
            LOG.info("User with username {} already exist. Nothing will be done. ", user.getUsername());
        } else {
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);

//            for (UserRole ur : userRoles) {
//                roleRepository.save(ur.getRole());
//            }

           // user.getUserRoles().addAll(userRoles);

            user.setSavingsAccount(accountService.createSavingsAccount());
            user.setCurrentAccount(accountService.createCurrentAccount());

            existingUser = userRepository.save(user);
        }

        return existingUser;
    }

    public boolean checkUserExists(String username, String email){
        if (checkUsernameExists(username) || checkEmailExists(username)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUsernameExists(String username) {
        if (null != findByUsername(username)) {
            return true;
        }

        return false;
    }

    public boolean checkEmailExists(String email) {
        if (null != findByEmail(email)) {
            return true;
        }

        return false;
    }

    public User saveUser (User user) {
        return userRepository.save(user);
    }


}
