package com.bootcamp.demo.business;

import Exceptions.ServiceException;
import com.bootcamp.demo.model.Account;

import java.util.concurrent.ExecutionException;

public interface Service<T> {
    String add(T elem) throws ServiceException;
    boolean delete(String username) throws ServiceException;
    boolean updatePassword(String username, String newPassword);
}