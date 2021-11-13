package com.bootcamp.demo.business;

import Exceptions.EncryptionException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


public interface iEncryptor {
    String encryptSHA256(String message) throws EncryptionException;
}
