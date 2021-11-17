package com.bootcamp.demo.data_access;

import java.util.concurrent.ExecutionException;


public interface Repo<T> {
    public String add(T elem) throws ExecutionException, InterruptedException;
    public boolean delete(String username);
    public boolean updatePassword(String username, String newPassword);
}