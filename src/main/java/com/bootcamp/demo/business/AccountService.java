package com.bootcamp.demo.business;

import com.bootcamp.demo.exception.ServiceException;
import com.bootcamp.demo.data_access.AbstractRepository;
import com.bootcamp.demo.model.Account;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
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

    public ValidationResponse accountValidation(Account account){

        ValidationResponse validationResponse = new ValidationResponse();

        if(StringUtils.isEmpty(account.getName()))
            validationResponse.addMessage("Name field is empty\n");

        if(StringUtils.isEmpty(account.getPassword()))
            validationResponse.addMessage("Password field is empty\n");

        if(StringUtils.isEmpty(account.getEmail()))
            validationResponse.addMessage("Email field is empty\n");

        if(StringUtils.isEmpty(account.getUsername()))
            validationResponse.addMessage("Username field is empty\n");

        if(StringUtils.isEmpty(account.getAddress()))
            validationResponse.addMessage("Address field is empty\n");

        if(StringUtils.isEmpty(account.getPhoneNumber()))
            validationResponse.addMessage("PhoneNumber field is empty\n");

        if(StringUtils.isEmpty(account.getDateOfBirth()))
            validationResponse.addMessage("DateOfBirth field is empty\n");


        final String nameExpression = "^([A-Z][a-z]*((\\s)))+[A-Z][a-z]*$";
        if(!account.getName().matches(nameExpression))
            validationResponse.addMessage("Wrong name format: must contain only letters and must be \"FirstName LastName\"  \n");

        final String phoneNumberExpression = "[a-zA-Z0-9]*$";
        if(!account.getUsername().matches(phoneNumberExpression))
            validationResponse.addMessage("Wrong username format: must contain only digits and letter \n");

        final String emailExpression = "^(.+)@(.+)$";
        if(!account.getEmail().matches(emailExpression))
            validationResponse.addMessage("Wrong email format\n");

        if(account.getPhoneNumber().length() != 10)
            validationResponse.addMessage("Wrong phoneNumber format: must have 10 digits \n");
        else {
            boolean match = Arrays.asList(account.getPhoneNumber().split(""))
                    .stream()
                    .allMatch(str -> str.matches("\\d+"));
            if(!match)
                validationResponse.addMessage("Wrong phoneNumber format: must contain only digits \n");

        }

        if(account.getDateOfBirth().length() != 10)
            validationResponse.addMessage("Wrong dateOfBirth format: must be MM-dd-yyyy \n");
        else {
            String pattern = "MM-dd-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            try {
                Date date = simpleDateFormat.parse(account.getDateOfBirth());
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Period period = Period.between(localDate, LocalDate.now());
                if (period.getYears() < 18)
                    validationResponse.addMessage("Minimum age must be 18 \n");

            } catch (ParseException e) {
                validationResponse.addMessage("Wrong dateOfBirth format: must be MM-dd-yyyy \n");
                e.printStackTrace();
            }
        }

        if(!validationResponse.getMessages().isEmpty())
            validationResponse.setErrorCode(true);

        return (validationResponse);

    }

    public String add(final Account account) throws ServiceException {
        try {
            account.setPassword(encryptor.encryptSHA256(account.getPassword()));
            if(!accountValidation(account).getErrorCode())
                return repository.add(account);
            else {
                throw new ServiceException(accountValidation(account).getMessages().toString());
            }
        } catch (ExecutionException | InterruptedException exception) {
            throw new ServiceException(exception.getMessage());
        }
    }

}