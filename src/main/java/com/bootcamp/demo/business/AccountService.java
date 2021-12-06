package com.bootcamp.demo.business;

import com.bootcamp.demo.exception.ServiceException;
import com.bootcamp.demo.data_access.AbstractRepository;
import com.bootcamp.demo.model.Account;
import com.bootcamp.demo.model.AccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@org.springframework.stereotype.Service
public class AccountService {
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

    public enum ErrorCode{
        INTERNAL(1),
        VALIDATION(2);
        private final int errorCode;
        ErrorCode(int i) {
            this.errorCode = i;
        }

        public int getErrorCode() {
            return errorCode;
        }
    }

    public String add(final Account account) throws ServiceException {
        try {
            account.setPassword(encryptor.encryptSHA256(account.getPassword()));
            ValidationResponse accountValidation = this.accountValidation.validate(account);
            if(accountValidation.getIsValid()){
                return repository.add(account);
            }
            else {
                throw new ServiceException(ErrorCode.VALIDATION, accountValidation.getMessages().toString());
            }
        } catch (ExecutionException | InterruptedException exception) {
            throw new ServiceException(ErrorCode.INTERNAL, exception.getMessage());
        }
    }

    public void delete(String username) throws ServiceException {
        try{
            repository.delete(username);

        } catch (ExecutionException | InterruptedException e) {
            throw new ServiceException(ErrorCode.INTERNAL, e.getMessage());
        }
        catch(IllegalArgumentException exception){
            throw new ServiceException(ErrorCode.VALIDATION, exception.getMessage());
        }
    }


    public AccountDetails retrieve(String username) throws ServiceException {
        try{
            Optional<Account> account = repository.retrieve(username);
            if(account.isPresent())
            {
                return createAccountDetails(account.get());
            }
            throw new ServiceException(ErrorCode.VALIDATION, "Account not found");
        } catch (ExecutionException | InterruptedException e) {
            throw new ServiceException(ErrorCode.INTERNAL, e.getMessage());
        }
    }


    public String updatePassword(String username, String oldPassword, String newPassword, String confirmNewPassword) throws ServiceException {

        if(!newPassword.equals(confirmNewPassword)){
            throw new ServiceException(ErrorCode.VALIDATION,"New password and Confirm new password field do not match");
        }

        String newPasswd = encryptor.encryptSHA256(newPassword);

        try {
            return repository.updatePassword(username, oldPassword, newPasswd);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.VALIDATION,e.getMessage());
        }
    }

    public List<Account> getAll() throws ServiceException {
        try {
            return repository.getAll();
        } catch (ExecutionException | InterruptedException e) {
            throw new ServiceException(ErrorCode.INTERNAL, e.getMessage());
        }
    }

    private AccountDetails createAccountDetails(final Account account){
        return  new AccountDetails(account.getName(),account.getEmail(),account.getUsername()
                ,account.getPhoneNumber(),account.getAddress(),account.getDateOfBirth());
    }
    public Account transformAccount(AccountDetails accountDetails) throws ServiceException {
        try {
            Optional<Account> account =  repository.retrieve(accountDetails.getUsername());
            if(account.isEmpty())
            {
                throw new ServiceException(ErrorCode.VALIDATION, "Account does not exist!");
            }
            Account searchedAccount = account.get();

            if (!Objects.equals(accountDetails.getName(), searchedAccount.getName())) {
                searchedAccount.setName(accountDetails.getName());
            }

            if (!Objects.equals(accountDetails.getEmail(), searchedAccount.getEmail())) {
                throw new ServiceException(ErrorCode.VALIDATION, "Email cannot be changed!");
            }

            if (!Objects.equals(accountDetails.getDateOfBirth(), searchedAccount.getDateOfBirth())) {
                searchedAccount.setDateOfBirth(accountDetails.getDateOfBirth());
            }

            if (!Objects.equals(accountDetails.getAddress(), searchedAccount.getAddress())) {
                searchedAccount.setAddress(accountDetails.getAddress());
            }

            if (!Objects.equals(accountDetails.getPhoneNumber(), searchedAccount.getPhoneNumber())) {
                searchedAccount.setPhoneNumber(accountDetails.getPhoneNumber());
            }

            return searchedAccount;
        } catch (ExecutionException | InterruptedException e) {
            throw new ServiceException(ErrorCode.INTERNAL, e.getMessage());
        }

    }

    public void update(AccountDetails account) throws ServiceException {
        Account transformedAccount = this.transformAccount(account);
        ValidationResponse validationResponse = accountValidation.validate(transformedAccount);
        if (validationResponse.getIsValid()) {
            try {
                repository.update(transformedAccount);
            } catch (InterruptedException | ExecutionException e) {
                throw new ServiceException(ErrorCode.INTERNAL, e.getMessage());
            }
            catch(Exception e){
                throw new ServiceException(ErrorCode.VALIDATION, e.getMessage());
            }
        } else {
            throw new ServiceException(ErrorCode.VALIDATION, validationResponse.getMessages().toString());
        }
    }


}