package com.bootcamp.demo.controller;

import com.bootcamp.demo.business.AccountService;
import com.bootcamp.demo.controller.dto.UpdatePasswordDTO;
import com.bootcamp.demo.exception.ServiceException;
import com.bootcamp.demo.business.Service;
import com.bootcamp.demo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AccountController {
    private final Service<Account> service;

    @Autowired
    public AccountController(Service<Account> service) {
        this.service = service;
    }

    private ResponseEntity<String> createResponse(ServiceException s) {
        HttpHeaders customHeaders = new HttpHeaders();
        customHeaders.add("ErrorCode",s.getErrorCode().toString());
        customHeaders.add("ErrorValue",Integer.toString(s.getErrorCode().getCode()));
        switch (s.getErrorCode()) {
            case SERVER:
                return ResponseEntity.status(500).headers(customHeaders).body(s.getMessage());
            case REPOSITORY:
                return ResponseEntity.status(409).headers(customHeaders).body(s.getMessage());
            default:
                return ResponseEntity.status(406).headers(customHeaders).body(s.getMessage());
        }
    }


    @PostMapping("/addAccount")
    public ResponseEntity<String> add(@RequestBody Account account) {
        try {
            return ResponseEntity.ok().body(service.add(account));
        } catch (ServiceException exception) {
            return this.createResponse(exception);
        }
    }

    @DeleteMapping("/deleteAccount/{username}")
    public ResponseEntity<String> delete(@PathVariable String username) {
        try {
            service.delete(username);
            return ResponseEntity.ok().body("account deleted successfully");
        } catch (ServiceException exception) {
            return this.createResponse(exception);
        }
    }

    @PatchMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordDTO dto) {
        try {
            return ResponseEntity.ok().body(service.updatePassword(dto.getUsername(), dto.getOldPassword(), dto.getNewPassword(), dto.getConfirmNewPassword()));
        } catch (ServiceException e) {
            return this.createResponse(e);
        }
    }

    @PostMapping("/getAll")
    ResponseEntity getAll() {
        try {
            return ResponseEntity.ok().body(service.getAll());
        } catch (ServiceException exception) {
             return this.createResponse(exception);
        }
    }
    
}
