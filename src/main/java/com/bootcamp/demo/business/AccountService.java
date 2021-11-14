package com.bootcamp.demo.business;

import Exceptions.EncryptionException;
import Exceptions.ServiceException;
import com.bootcamp.demo.data_access.Repo;
import com.bootcamp.demo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutionException;


@org.springframework.stereotype.Service
public class AccountService implements Service<Account> {
    Repo<Account> repository;

    @Autowired
    public void setRepository(Repo<Account> repository) {
        this.repository = repository;
    }

    Encryptor encryptor;

//    @Autowired
//    public AccountService(Repo<Account> repository) {
//        this.repository = repository;
//    }

    @Autowired
    public void setEncryptor(PasswordEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    public String add(final Account account) throws ServiceException {
        //todo validate, make date iso8601 compliant?
        try {
            account.setPassword(encryptor.encryptSHA256(account.getPassword()));
            System.out.println(account.getPassword());
            return repository.add(account);
        }
        catch(ExecutionException | InterruptedException  exception){
            throw new ServiceException(exception.getMessage());
        }
    }

}
