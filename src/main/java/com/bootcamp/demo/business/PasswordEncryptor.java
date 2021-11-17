package com.bootcamp.demo.business;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordEncryptor implements Encryptor {
    BCryptPasswordEncoder passwordEncoder;

    public PasswordEncryptor() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encryptSHA256(String message){
        return passwordEncoder.encode(message);
    }


}