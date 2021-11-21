package com.bootcamp.demo.business;

import com.bootcamp.demo.exception.ServiceException;

public interface Service<T> {
    String add(T elem) throws ServiceException;
    String updatePassword(String username, String newPassword) throws ServiceException;
}