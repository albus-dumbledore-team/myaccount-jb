package com.bootcamp.demo.business;

import Exceptions.ServiceException;
import com.bootcamp.demo.data_access.Repo;
import com.bootcamp.demo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ExecutionException;


@org.springframework.stereotype.Service
public class AccountService implements Service<Account> {
    Repo<Account> repository;
    Encryptor encryptor;

    @Autowired
    public void setEncryptor(Encryptor encryptor) {
        this.encryptor = encryptor;
    }

    @Autowired
    public void setRepository(Repo<Account> accountRepo){
        this.repository = accountRepo;
    }

    public String add(final Account account) throws ServiceException {
        //todo validate, make date iso8601 compliant?
        try {
            account.setPassword(encryptor.encryptSHA256(account.getPassword()));
            System.out.println(account.getPassword());
            return repository.add(account);
        } catch (ExecutionException | InterruptedException exception) {
            throw new ServiceException(exception.getMessage());
        }
    }

    @Override
    public Account findOne(String username) throws ServiceException, ExecutionException, InterruptedException {
        return repository.findOne(username);
    }

    @Override
    public boolean delete(String username) {
        return repository.delete(username);
    }

    public List<Account> getAll() throws ServiceException {
        try {
            return repository.getAll();
        } catch (ExecutionException | InterruptedException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
