package com.bootcamp.demo.Controller;

import Exceptions.ControllerException;
import Exceptions.ServiceException;
import com.bootcamp.demo.business.Service;
import com.bootcamp.demo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
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
    void add(@RequestBody Account account) throws ControllerException {
        try {
            service.add(account);
        }catch(ServiceException exception){
            throw new ControllerException(exception.getMessage());
        }
    }
}
