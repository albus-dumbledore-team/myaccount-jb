package com.bootcamp.demo.business;

import com.bootcamp.demo.Exceptions.ServiceException;

public interface Service<T> {
    String add(T elem) throws ServiceException;
}
