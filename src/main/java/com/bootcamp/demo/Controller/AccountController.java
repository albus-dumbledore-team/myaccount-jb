package com.bootcamp.demo.Controller;

import com.bootcamp.demo.business.Service;
import com.bootcamp.demo.controller.DTOs.UpdatePasswordDTO;
import com.bootcamp.demo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    private final Service<Account> service;

    @Autowired
    public AccountController(Service<Account> service) {
        this.service = service;
    }

    @PatchMapping("/updatePassword")
    public void updatePassword(@RequestBody UpdatePasswordDTO dto) {
        service.updatePassword(dto.getUsername(), dto.getPassword());
    }
}