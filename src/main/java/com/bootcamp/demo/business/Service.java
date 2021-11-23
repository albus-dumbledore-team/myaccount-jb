package com.bootcamp.demo.business;

import com.bootcamp.demo.exception.ServiceException;

public interface Service<T> {
    String add(T elem) throws ServiceException;
    void delete(String username) throws ServiceException;
    String updatePassword(String username, String oldPassword, String newPassword, String confirmNewPassword) throws ServiceException;
}
