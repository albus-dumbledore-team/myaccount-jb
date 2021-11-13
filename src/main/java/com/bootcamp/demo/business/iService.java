package com.bootcamp.demo.business;

import Exceptions.ServiceException;
import com.bootcamp.demo.model.Account;

import java.util.concurrent.ExecutionException;

public interface iService<T> {
    String add(T elem) throws ServiceException;
}
