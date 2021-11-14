package com.bootcamp.demo.business;
import Exceptions.EncryptionException;
import org.springframework.context.annotation.Bean;
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
//    @Bean
    public String encryptSHA256(String message){
        return passwordEncoder.encode(message);
    }

//    @Bean
//    public String encryptSHA256(String message) throws EncryptionException {
//        try {
//            return this.toHexString(this.getSHA(message));
//        }catch(NoSuchAlgorithmException exception){
//            throw new EncryptionException(exception.getMessage());
//        }
//    }
//
//
//    private byte[] getSHA(String input) throws NoSuchAlgorithmException {
//        //generates a hash for SHA256 as a byte array
//        // MessageDigest instance for hashing using SHA256
//        MessageDigest md = MessageDigest.getInstance("SHA-256");
//
//        // digest() method called to calculate message digest of an input and return array of byte
//        return md.digest(input.getBytes(StandardCharsets.UTF_8));
//    }
//
//    private String toHexString(byte[] hash)
//    {
//        //converts the byte array back to string format
//        // Convert byte array of hash into digest
//        BigInteger number = new BigInteger(1, hash);
//
//        // Convert the digest into hex value
//        StringBuilder hexString = new StringBuilder(number.toString(16));
//
//        // Pad with leading zeros
//        while (hexString.length() < 32)
//        {
//            hexString.insert(0, '0');
//        }
//        return hexString.toString();
//    }

}
