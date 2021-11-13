package com.bootcamp.demo.business;

import Exceptions.EncryptionException;
import Exceptions.ServiceException;
import com.bootcamp.demo.data_access.iRepository;
import com.bootcamp.demo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.util.concurrent.ExecutionException;


@Service
public class AccountService implements iService<Account>{
    iRepository<Account> repository;
    iEncryptor encryptor;

    @Autowired
    public AccountService(iRepository<Account> repository, iEncryptor encryptor) {
        this.repository = repository;
        this.encryptor = encryptor;
    }

    public String add(final Account account) throws ServiceException {
        try {
            account.setPassword(encryptor.encryptSHA256(account.getPassword()));
            System.out.println(account.getPassword());
            return repository.add(account);
        }
        catch(EncryptionException | ExecutionException | InterruptedException  exception){
            throw new ServiceException(exception.getMessage());
        }
    }

}
