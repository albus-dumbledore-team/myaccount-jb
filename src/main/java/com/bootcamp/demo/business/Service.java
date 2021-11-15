package com.bootcamp.demo.business;

import Exceptions.ServiceException;
import com.bootcamp.demo.model.Account;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface Service<T> {
    String add(T elem) throws ServiceException;
    boolean delete(String username) throws ServiceException;
    Account findOne(String element) throws ServiceException, ExecutionException, InterruptedException;
    List<Account> getAll() throws ExecutionException, InterruptedException, ServiceException;
}
