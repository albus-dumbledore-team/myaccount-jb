package com.bootcamp.demo.controller;

import com.bootcamp.demo.business.PromotionService;
import com.bootcamp.demo.exception.ServiceException;
import com.bootcamp.demo.model.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PromotionController {
    private final PromotionService promotionService;
    private final ResponseHandler responseHandler;

    @Autowired
    public PromotionController(PromotionService promotionService, ResponseHandler handler) {
        this.promotionService = promotionService;
        responseHandler = handler;
    }

    @PostMapping("/addPromotion")
    public ResponseEntity<String> add(@RequestBody Promotion promotion) {
        try {
            return ResponseEntity.ok().body(promotionService.add(promotion));
        } catch (ServiceException exception) {
            return responseHandler.createResponse(exception);
        }
    }

    @GetMapping("/viewPromotion/{code}")
    @ResponseBody
    public ResponseEntity viewAccount(@PathVariable String code) {
        try {
            return ResponseEntity.ok(promotionService.retrieve(code));
        } catch (ServiceException exception) {
            return responseHandler.createResponse(exception);
        }
    }
}
