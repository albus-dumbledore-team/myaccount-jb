package com.bootcamp.demo.data_access;

import com.bootcamp.demo.model.Account;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;


public interface AbstractRepository<T> {
    public String add(T elem) throws ExecutionException, InterruptedException;
    public boolean delete(String username);
    public Account findOne(String element) throws ExecutionException, InterruptedException;
    public List<Account> getAll() throws ExecutionException, InterruptedException;
}