package com.bootcamp.demo.controller;

import com.bootcamp.demo.exception.ServiceException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@Configuration
public class ResponseHandler {
    public ResponseEntity<String> createResponse(ServiceException s) {
        HttpHeaders customHeaders = new HttpHeaders();
        customHeaders.add("ErrorCode", s.getErrorCode().toString());
        customHeaders.add("ErrorValue", Integer.toString(s.getErrorCode().getErrorCode()));
        ServiceException.ErrorCode errorCode = s.getErrorCode();
        if (errorCode == ServiceException.ErrorCode.INTERNAL) {
            return ResponseEntity.status(500).headers(customHeaders).body(s.getMessage());
        } else return ResponseEntity.status(406).headers(customHeaders).body(s.getMessage());
    }
}
