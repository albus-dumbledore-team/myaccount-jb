package com.bootcamp.demo.business;

import com.bootcamp.demo.exception.ServiceException;
import com.bootcamp.demo.data_access.AbstractRepository;
import com.bootcamp.demo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
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

    public String accountValidation(Account account) throws ServiceException {

        StringBuilder stringBuilder = new StringBuilder();
        if(account.getName().isEmpty() || account.getPassword().isEmpty() || account.getEmail().isEmpty() || account.getUsername().isEmpty() || account.getAddress().isEmpty() || account.getPhoneNumber().isEmpty() || account.getDateOfBirth().isEmpty())
            stringBuilder.append("Empty fields");

        String expression = "^([A-Z][a-z]*((\\s)))+[A-Z][a-z]*$";
        if(!account.getName().matches(expression))
            stringBuilder.append("Wrong name format: must contain only letters and must be \"FirstName LastName\"  \n");

        expression = "[a-zA-Z0-9]*$";
        if(!account.getUsername().matches(expression))
            stringBuilder.append("Wrong username format: must contain only digits and letter\n");

        if(account.getPhoneNumber().length() != 10)
            stringBuilder.append("Wrong phoneNumber format: must have 10 digits\n");
        else {
            for (char c : account.getPhoneNumber().toCharArray()) {
                if (!Character.isDigit(c))
                    stringBuilder.append("Wrong phoneNumber format: must contain only digits\n");
            }
        }

        if(account.getDateOfBirth().length() != 10)
            stringBuilder.append("Wrong dateOfBirth format: must be MM-dd-yyyy\n");
        else {
            String pattern = "MM-dd-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            try {
                Date date = simpleDateFormat.parse(account.getDateOfBirth());
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Period period = Period.between(localDate, LocalDate.now());
                if (period.getYears() < 18)
                    stringBuilder.append("Minimum age must be 18\n");
            } catch (ParseException e) {
                stringBuilder.append("Wrong dateOfBirth format: must be MM-dd-yyyy\n");
                e.printStackTrace();
            }
        }

        if(stringBuilder.length() != 0)
            throw new ServiceException(stringBuilder.toString());
        else {
            return ("OK");
        }
    }

    public String add(final Account account) throws ServiceException {
        try {
            account.setPassword(encryptor.encryptSHA256(account.getPassword()));
            if(accountValidation(account).equals("OK"))
                return repository.add(account);
            else
                throw new ServiceException(accountValidation(account));
        } catch (ExecutionException | InterruptedException exception) {
            throw new ServiceException(exception.getMessage());
        }
    }

}