package com.bootcamp.demo.controller;

import com.bootcamp.demo.exception.ControllerException;
import com.bootcamp.demo.exception.ServiceException;
import com.bootcamp.demo.business.Service;
import com.bootcamp.demo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
