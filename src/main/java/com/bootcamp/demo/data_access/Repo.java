package com.bootcamp.demo.data_access;

import com.bootcamp.demo.model.Account;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;


public interface Repo<T> {
    public String add(T elem) throws ExecutionException, InterruptedException;
}