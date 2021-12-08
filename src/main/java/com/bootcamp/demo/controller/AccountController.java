package com.bootcamp.demo.controller;

import com.bootcamp.demo.business.AccountService;
import com.bootcamp.demo.controller.dto.UpdatePasswordDTO;
import com.bootcamp.demo.exception.ServiceException;
import com.bootcamp.demo.model.Account;
import com.bootcamp.demo.model.AccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
public class AccountController {
    private final AccountService service;
    private final ResponseHandler responseHandler;

    @Autowired
    public AccountController(AccountService service, ResponseHandler handler) {
        this.service = service;
        responseHandler = handler;
    }

    @PostMapping("/addAccount")
    public ResponseEntity<String> add(@RequestBody Account account) {
        try {
            return ResponseEntity.ok().body(service.add(account));
        } catch (ServiceException exception) {
            return responseHandler.createResponse(exception);
        }
    }

    @DeleteMapping("/deleteAccount/{username}")
    public ResponseEntity<String> delete(@PathVariable String username) {
        try {
            service.delete(username);
            return ResponseEntity.ok().body("Account deleted successfully");
        } catch (ServiceException e) {
            return responseHandler.createResponse(e);
        }
    }

    @PatchMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordDTO dto) {
        try {
            return ResponseEntity.ok().body(service.updatePassword(dto.getUsername(), dto.getOldPassword(), dto.getNewPassword(), dto.getConfirmNewPassword()));
        } catch (ServiceException e) {
            return responseHandler.createResponse(e);
        }
    }

    @GetMapping("/getAll")
    ResponseEntity getAll() {
        try {
            return ResponseEntity.ok().body(service.getAll());
        } catch (ServiceException exception) {
            return responseHandler.createResponse(exception);
        }
    }

    @GetMapping("/viewAccount/{username}")
    @ResponseBody
    public ResponseEntity viewAccount(@PathVariable String username) {
        try {
            return ResponseEntity.ok(service.retrieve(username));
        } catch (ServiceException exception) {
            return responseHandler.createResponse(exception);

        }
    }

    @PutMapping("/editAccount")
    public ResponseEntity<String> update(@RequestBody AccountDetails accountDetails) {
        try {
            service.update(accountDetails);
            return ResponseEntity.status(200).body("Successfully updated.");
        } catch (ServiceException exception) {
            return responseHandler.createResponse(exception);
        }
    }

    @PutMapping("/addPromotionToAccount{accountId}/{code}")
    public ResponseEntity<String> addPromotionToAccount(@PathVariable String accountId, @PathVariable String code) {
        try {
            service.addPromotionToAccount(accountId, code);
            return ResponseEntity.status(200).body("Successfully added.");
        } catch (ServiceException exception) {
            return responseHandler.createResponse(exception);
        }
    }

    @GetMapping("/viewAccountPromotions/{username}")
    @ResponseBody
    public ResponseEntity viewAccountPromotions(@PathVariable String username) {
        try {
            return ResponseEntity.ok(service.getAccountPromotions(username));
        } catch (ServiceException exception) {
            return responseHandler.createResponse(exception);
        }
    }

}
