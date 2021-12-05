package com.bootcamp.demo.controller;

import com.bootcamp.demo.business.AccountService;
import com.bootcamp.demo.exception.ControllerException;
import com.bootcamp.demo.exception.ServiceException;
import com.bootcamp.demo.model.Account;
import com.bootcamp.demo.model.AccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class AccountController {
    private final AccountService service;

    @Autowired
    public AccountController(AccountService service) {
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
    public ResponseEntity<String> update(@RequestBody AccountDetails accountDetails)  {
        try {
            service.update(accountDetails);
            return ResponseEntity.status(200).body("Successfully updated.");
        } catch (ServiceException exception) {
            return ResponseEntity.status(400).body(exception.getMessage());
        }
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
