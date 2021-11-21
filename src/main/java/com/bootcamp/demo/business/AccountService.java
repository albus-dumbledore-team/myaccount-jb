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
import java.util.List;
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

    public boolean accountValidation(Account account){
        if(account.getName().isEmpty() || account.getPassword().isEmpty() || account.getEmail().isEmpty() || account.getUsername().isEmpty() || account.getAddress().isEmpty() || account.getPhoneNumber().isEmpty() || account.getDateOfBirth().isEmpty())
            return false;

        String expression = "^([A-Z][a-z]*((\\s)))+[A-Z][a-z]*$";
        if(!account.getName().matches(expression))
            return false;

        expression = "[a-zA-Z0-9]*$";
        if(!account.getUsername().matches(expression))
            return false;

        if(account.getPhoneNumber().length() != 10)
            return false;

        for(char c: account.getPhoneNumber().toCharArray()){
            if(!Character.isDigit(c))
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


    @Override
    public boolean updatePassword(String username, String newPassword) {
        // TODO: password validation
        String password = encryptor.encryptSHA256(newPassword);
        return repository.updatePassword(username, password);
    }
}