package com.bootcamp.demo.business;

import com.bootcamp.demo.exception.ServiceException;
import com.bootcamp.demo.model.Account;

import java.util.concurrent.ExecutionException;

public interface Service<T> {
    String add(T elem) throws ServiceException;

    void delete(String username) throws ServiceException;

    T update(T element) throws ServiceException, ExecutionException, InterruptedException;

    T findOne(String username) throws ServiceException, ExecutionException, InterruptedException;
}
