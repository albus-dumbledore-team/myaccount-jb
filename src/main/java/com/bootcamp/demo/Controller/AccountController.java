package com.bootcamp.demo.Controller;

import Exceptions.ControllerException;
import Exceptions.ServiceException;
import com.bootcamp.demo.business.iService;
import com.bootcamp.demo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class AccountController {
    private final iService<Account> service;

    @Autowired
    public AccountController(iService<Account> service) {
        this.service = service;
    }

    @PostMapping("/addAccount")
    void add(@RequestBody Account account) throws ControllerException {
        try {
            service.add(account);
        }catch(ServiceException exception){
            throw new ControllerException(exception.getMessage());
        }
    }
}
