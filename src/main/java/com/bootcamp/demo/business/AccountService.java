package com.bootcamp.demo.business;

import com.bootcamp.demo.Exceptions.ServiceException;
import com.bootcamp.demo.data_access.AbstractRepository;
import com.bootcamp.demo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutionException;


@org.springframework.stereotype.Service
public class AccountService implements Service<Account> {
    AbstractRepository<Account> repository;
    Encryptor encryptor;

    @Autowired
    public void setRepository(AbstractRepository<Account> repository) {
        this.repository = repository;
    }


    @Autowired
    public void setEncryptor(Encryptor encryptor) {
        this.encryptor = encryptor;
    }

    public String add(final Account account) throws ServiceException {
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
