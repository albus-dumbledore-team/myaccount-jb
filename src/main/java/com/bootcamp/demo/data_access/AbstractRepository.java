package com.bootcamp.demo.data_access;

import com.bootcamp.demo.model.Account;

import java.util.concurrent.ExecutionException;


public interface AbstractRepository<T> {
    String add(T elem) throws ExecutionException, InterruptedException;
    public void delete(String username);
    public T findOne(String username) throws ExecutionException, InterruptedException;
    public T update(T username) throws ExecutionException, InterruptedException;
}