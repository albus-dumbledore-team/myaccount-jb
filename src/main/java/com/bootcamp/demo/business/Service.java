package com.bootcamp.demo.business;

import com.bootcamp.demo.exception.ServiceException;

public interface Service<T> {
    String add(T elem) throws ServiceException;
    boolean updatePassword(String username, String newPassword);
}