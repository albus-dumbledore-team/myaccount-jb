package com.bootcamp.demo.Controller;

import Exceptions.ControllerException;
import Exceptions.ServiceException;
import com.bootcamp.demo.business.Service;
import com.bootcamp.demo.model.Account;
import com.bootcamp.demo.model.AccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
        } catch (ServiceException exception) {
            throw new ControllerException(exception.getMessage());
        }
    }

    @DeleteMapping("/deleteAccount/{username}")
    public void delete(@PathVariable String username) throws ControllerException {
        try {
            service.delete(username);
            System.out.println("[DELETE]: Account with username '" + username + "' was deleted successfully.");
        } catch (ServiceException exception) {
            throw new ControllerException(exception.getMessage());
        }
    }
    @GetMapping("/viewAccount/{username}")
    @ResponseBody
    AccountDetails viewAccount(@PathVariable String username) throws ControllerException {
        try {
            Account account = service.findOne(username);
            AccountDetails accountDetails = new AccountDetails(account.getName(), account.getEmail(), account.getUsername(), account.getPhoneNumber(), account.getAddress(), account.getDateOfBirth());
            System.out.println("[VIEW]: Account with username " + account);
            return accountDetails;
        } catch (ServiceException exception) {
            throw new ControllerException(exception.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

