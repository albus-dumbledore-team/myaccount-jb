package com.bootcamp.demo.business;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import java.math.BigInteger;
//import java.nio.charset.StandardCharsets;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;

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
