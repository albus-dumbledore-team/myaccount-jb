package com.bootcamp.demo.controller;

import com.bootcamp.demo.business.AccountService;
import com.bootcamp.demo.controller.dto.UpdatePasswordDTO;
import com.bootcamp.demo.exception.ServiceException;
import com.bootcamp.demo.model.Account;
import com.bootcamp.demo.model.AccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
public class AccountController {
    private final AccountService service;

    @Autowired
    public AccountController(AccountService service) {
        this.service = service;
    }

    private ResponseEntity<String> createResponse(ServiceException s) {
        HttpHeaders customHeaders = new HttpHeaders();
        customHeaders.add("ErrorCode",s.getErrorCode().toString());
        customHeaders.add("ErrorValue",Integer.toString(s.getErrorCode().getErrorCode()));
        AccountService.ErrorCode errorCode = s.getErrorCode();
        if (errorCode == AccountService.ErrorCode.INTERNAL) {
            return ResponseEntity.status(500).headers(customHeaders).body(s.getMessage());
        }
        else return ResponseEntity.status(406).headers(customHeaders).body(s.getMessage());
    }

    @PostMapping("/addAccount")
    public ResponseEntity<String> add(@RequestBody Account account) {
        try {
            return ResponseEntity.ok().body(service.add(account));
        } catch (ServiceException exception) {
            return createResponse(exception);
        }
    }

    @DeleteMapping("/deleteAccount/{username}")
    public ResponseEntity<String> delete(@PathVariable String username){
        try {
            service.delete(username);
            return ResponseEntity.ok().body("Account deleted successfully");
        } catch (ServiceException e) {
            return createResponse(e);
        }
    }

    @PatchMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordDTO dto) {
        try {
            return ResponseEntity.ok().body(service.updatePassword(dto.getUsername(), dto.getOldPassword(), dto.getNewPassword(), dto.getConfirmNewPassword()));
        } catch (ServiceException e) {
            return createResponse(e);
        }
    }

    @GetMapping("/getAll")
    ResponseEntity getAll() {
        try {
            return ResponseEntity.ok().body(service.getAll());
        } catch (ServiceException exception) {
            return createResponse(exception);
        }
    }
    @GetMapping("/viewAccount/{username}")
    @ResponseBody
    public ResponseEntity viewAccount(@PathVariable String username){
        try {
            return ResponseEntity.ok(service.retrieve(username));
        } catch (ServiceException exception) {
            return createResponse(exception);

        }
    }

    @PutMapping("/editAccount")
    public ResponseEntity<String> update(@RequestBody AccountDetails accountDetails)  {
        try {
            service.update(accountDetails);
            return ResponseEntity.status(200).body("Successfully updated.");
        } catch (ServiceException exception) {
            return createResponse(exception);
        }
    }

}
