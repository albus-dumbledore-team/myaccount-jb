package com.bootcamp.demo.controller;

import com.bootcamp.demo.business.Service;
import com.bootcamp.demo.exception.ControllerException;
import com.bootcamp.demo.exception.ServiceException;
import com.bootcamp.demo.model.Account;
import com.bootcamp.demo.model.AccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

@RestController
public class AccountController {
    private final Service<Account> service;

    @Autowired
    public AccountController(Service<Account> service) {
        this.service = service;
    }

    @PostMapping("/addAccount")
    ResponseEntity<String> add(@RequestBody Account account) {
        try {
            return ResponseEntity.ok().body(service.add(account));
        } catch (ServiceException exception) {
            return ResponseEntity.status(406).body(exception.getMessage());
        }
    }

    @PutMapping("/editAccount")
    ResponseEntity<Account> update(@RequestBody AccountDetails accountDetails) throws ControllerException {
        try {
            Account searchedAccount = service.findOne(accountDetails.getUsername());

            if (searchedAccount != null) {
                Account account = new Account(
                        accountDetails.getName(),
                        accountDetails.getEmail(),
                        accountDetails.getUsername(),
                        searchedAccount.getPassword(),
                        accountDetails.getPhoneNumber(),
                        accountDetails.getAddress(),
                        accountDetails.getDateOfBirth()
                );

                if (!Objects.equals(searchedAccount.getEmail(), accountDetails.getEmail())) {
                    // TODO: Find a solution to return the error message: "Email cannot be changed!"
                    return ResponseEntity.status(400).body(null);
                }

                return  ResponseEntity.status(200).body(this.service.update(account));
            }
        } catch (ServiceException | InterruptedException | ExecutionException exception) {
            throw new ControllerException(exception.getMessage());
        }

        return null;
    }


    @DeleteMapping("/deleteAccount/{username}")
    public void delete(@PathVariable String username) throws ControllerException {
        try {
            service.delete(username);
        } catch (ServiceException exception) {
            throw new ControllerException(exception.getMessage());
        }
    }
}
