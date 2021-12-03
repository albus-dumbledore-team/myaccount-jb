package com.bootcamp.demo.business;

import com.bootcamp.demo.exception.ServiceException;
import com.bootcamp.demo.data_access.AbstractRepository;
import com.bootcamp.demo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import java.util.concurrent.ExecutionException;

@org.springframework.stereotype.Service
public class AccountService implements Service<Account> {
    AbstractRepository<Account> repository;
    Encryptor encryptor;
    AccountValidation accountValidation = new AccountValidation();

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
            ValidationResponse validationResponse = accountValidation.validate(account);
            if(validationResponse.getIsValid()){
                return repository.add(account);
            }
            else {
                throw new ServiceException(validationResponse.getMessages().toString());
            }
        } catch (ExecutionException | InterruptedException exception) {
            throw new ServiceException(exception.getMessage());
        }
    }

    @Override
    public void delete(String username) {
        repository.delete(username);
    }

    @Override
    public String updatePassword(String username, String oldPassword, String newPassword, String confirmNewPassword) throws ServiceException {

        if(!newPassword.equals(confirmNewPassword)){
            throw new ServiceException("New password and Confirm new password field do not match");
        }

        String newPasswd = encryptor.encryptSHA256(newPassword);

        try {
            return repository.updatePassword(username, oldPassword, newPasswd);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<Account> getAll() throws ServiceException {
        try {
            return repository.getAll();
        } catch (ExecutionException | InterruptedException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}