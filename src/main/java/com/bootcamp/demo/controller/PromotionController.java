package com.bootcamp.demo.controller;

import com.bootcamp.demo.business.PromotionService;
import com.bootcamp.demo.exception.ServiceException;
import com.bootcamp.demo.model.Account;
import com.bootcamp.demo.model.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PromotionController {
    private final PromotionService promotionService;

    @Autowired
    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    //todo create separate class for this & inject as dependency
    private ResponseEntity<String> createResponse(ServiceException s) {
        HttpHeaders customHeaders = new HttpHeaders();
        customHeaders.add("ErrorCode",s.getErrorCode().toString());
        customHeaders.add("ErrorValue",Integer.toString(s.getErrorCode().getErrorCode()));
        ServiceException.ErrorCode errorCode = s.getErrorCode();
        if (errorCode == ServiceException.ErrorCode.INTERNAL) {
            return ResponseEntity.status(500).headers(customHeaders).body(s.getMessage());
        }
        else return ResponseEntity.status(406).headers(customHeaders).body(s.getMessage());
    }

    @PostMapping("/addPromotion")
    public ResponseEntity<String> add(@RequestBody Promotion promotion) {
        try {
            return ResponseEntity.ok().body(promotionService.add(promotion));
        } catch (ServiceException exception) {
            return createResponse(exception);
        }
    }

    @GetMapping("/viewPromotion/{code}")
    @ResponseBody
    public ResponseEntity viewAccount(@PathVariable String code){
        try {
            return ResponseEntity.ok(promotionService.retrieve(code));
        } catch (ServiceException exception) {
            return createResponse(exception);
        }
    }
}
