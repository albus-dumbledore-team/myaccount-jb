package com.bootcamp.demo.controller;

import com.bootcamp.demo.business.AccountService;
import com.bootcamp.demo.controller.dto.UpdatePasswordDTO;
import com.bootcamp.demo.exception.ControllerException;
import com.bootcamp.demo.exception.ServiceException;
import com.bootcamp.demo.business.Service;
import com.bootcamp.demo.model.Account;
import com.bootcamp.demo.model.AccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/deleteAccount/{username}")
    public void delete(@PathVariable String username) throws ControllerException {
        service.delete(username);
    }

    @PatchMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordDTO dto) {
        try {
            return ResponseEntity.ok().body(service.updatePassword(dto.getUsername(), dto.getOldPassword(), dto.getNewPassword(), dto.getConfirmNewPassword()));
        } catch (ServiceException e) {
            return ResponseEntity.status(406).body(e.getMessage());
        }
    }

    @PostMapping("/getAll")
    ResponseEntity getAll() {
        try {
            return ResponseEntity.ok().body(service.getAll());
        } catch (ServiceException exception) {
            return ResponseEntity.status(406).body(exception.getMessage());
        }
    }
    @GetMapping("/viewAccount/{username}")
    @ResponseBody
    public ResponseEntity viewAccount(@PathVariable String username) throws ControllerException {
        try {
            return ResponseEntity.ok(service.retrieve(username));
        } catch (ServiceException exception) {
            return ResponseEntity.status(406).body(exception.getMessage());

        }
    }
}
