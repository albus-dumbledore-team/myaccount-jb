package com.bootcamp.demo.business;

import com.bootcamp.demo.data_access.AbstractRepository;
import com.bootcamp.demo.data_access.AccountRepository;
import com.bootcamp.demo.exception.ServiceException;
import com.bootcamp.demo.model.Account;
import com.bootcamp.demo.model.AccountDetails;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutionException;


@org.springframework.stereotype.Service
public class AccountService {
    AccountRepository repository;
    Encryptor encryptor;

    @Autowired
    public void setRepository(AccountRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setEncryptor(Encryptor encryptor) {
        this.encryptor = encryptor;
    }

    public boolean accountValidation(Account account) {
        if (account.getName().isEmpty() || account.getPassword().isEmpty() || account.getEmail().isEmpty() || account.getUsername().isEmpty() || account.getAddress().isEmpty() || account.getPhoneNumber().isEmpty() || account.getDateOfBirth().isEmpty())
            return false;

        String expression = "^([A-Z][a-z]*((\\s)))+[A-Z][a-z]*$";
        if (!account.getName().matches(expression))
            return false;

        expression = "[a-zA-Z0-9]*$";
        if (!account.getUsername().matches(expression))
            return false;

        if (account.getPhoneNumber().length() != 10)
            return false;

        for (char c : account.getPhoneNumber().toCharArray()) {
            if (!Character.isDigit(c))
                return false;
        }

        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = simpleDateFormat.parse(account.getDateOfBirth());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period period = Period.between(localDate, LocalDate.now());

        return (period.getYears() > 18);
    }

    public String add(final Account account) throws ServiceException {
        try {
            account.setPassword(encryptor.encryptSHA256(account.getPassword()));
            return repository.add(account);
        } catch (ExecutionException | InterruptedException exception) {
            throw new ServiceException(exception.getMessage());
        }
    }

    public Account findOne(String username) throws ServiceException, ExecutionException, InterruptedException {
        return repository.findOne(username);
    }


    public void update(AccountDetails account) throws ServiceException {
        Account transformAccount = this.transformAccount(account);

        if (accountValidation(transformAccount)) {
            try {
                repository.update(transformAccount);
            } catch (InterruptedException | ExecutionException e) {
                throw new ServiceException(e.getMessage());
            }
        } else {
            throw new ServiceException("Validation error");
        }
    }

    public Account transformAccount(AccountDetails accountDetails) throws ServiceException {
        try {
            Account searchedAccount = repository.findOne(accountDetails.getUsername());

            if (accountDetails.getName() != searchedAccount.getName()) {
                searchedAccount.setName(accountDetails.getName());
            }

            if (!Objects.equals(accountDetails.getEmail(), searchedAccount.getEmail())) {
                throw new ServiceException("Email cannot be changed!");
            }

            if (accountDetails.getDateOfBirth() != searchedAccount.getDateOfBirth()) {
                searchedAccount.setDateOfBirth(accountDetails.getDateOfBirth());
            }

            if (accountDetails.getAddress() != searchedAccount.getAddress()) {
                searchedAccount.setAddress(accountDetails.getAddress());
            }

            if (accountDetails.getPhoneNumber() != searchedAccount.getPhoneNumber()) {
                searchedAccount.setPhoneNumber(accountDetails.getPhoneNumber());
            }

            return searchedAccount;
        } catch (ExecutionException | InterruptedException e) {
            throw new ServiceException(e.getMessage());
        }

    }

    public void delete(String username) throws ServiceException {
        repository.delete(username);
    }
}