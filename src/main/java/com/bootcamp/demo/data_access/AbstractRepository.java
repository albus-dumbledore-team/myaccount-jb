package com.bootcamp.demo.data_access;

import com.bootcamp.demo.model.Account;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;


public interface AbstractRepository<T> {
    String add(T elem) throws ExecutionException, InterruptedException;
    boolean updatePassword(String username, String newPassword);
}