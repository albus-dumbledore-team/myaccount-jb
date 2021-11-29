package com.bootcamp.demo.business;

import com.bootcamp.demo.model.Account;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

public class AccountValidation {

    private Account account;
    private ValidationResponse validationResponse;

    public AccountValidation(Account account) {
        this.account = account;
        this.validationResponse = new ValidationResponse();
    }

    private void nameValidator() {
        if (StringUtils.isEmpty(account.getName()))
            validationResponse.addMessage("\nName field is empty");
        else {
            final String nameExpression = "^([A-Z][a-z]*((\\s)))+[A-Z][a-z]*$";
            if (!account.getName().matches(nameExpression))
                validationResponse.addMessage("\nWrong name format: must contain only letters and must be \"FirstName LastName\"  ");
        }
    }

    public void emailValidator() {
        if (StringUtils.isEmpty(account.getEmail()))
            validationResponse.addMessage("\nEmail field is empty");
        else {
            final String emailExpression = "^(.+)@(.+)$";
            if (!account.getEmail().matches(emailExpression))
                validationResponse.addMessage("\nWrong email format");
        }
    }

    public void usernameValidator() {
        if (StringUtils.isEmpty(account.getUsername()))
            validationResponse.addMessage("\nUsername field is empty");
        else {
            final String userNameExpression = "[a-zA-Z0-9]*$";
            if (!account.getUsername().matches(userNameExpression))
                validationResponse.addMessage("\nWrong username format: must contain only digits and letter ");
        }
    }

    public void passwordValidator() {
        if (StringUtils.isEmpty(account.getPassword()))
            validationResponse.addMessage("\nPassword field is empty");
    }

    public void phoneNumberValidator() {
        if (StringUtils.isEmpty(account.getPhoneNumber()))
            validationResponse.addMessage("\nPhoneNumber field is empty");
        else if (account.getPhoneNumber().length() != 10)
            validationResponse.addMessage("\nWrong phoneNumber format: must have 10 digits ");
        else {
            boolean match = Arrays.asList(account.getPhoneNumber().split(""))
                    .stream()
                    .allMatch(str -> str.matches("\\d+"));
            if (!match)
                validationResponse.addMessage("\nWrong phoneNumber format: must contain only digits ");
        }
    }

    private void addressValidator() {
        if (StringUtils.isEmpty(account.getAddress()))
            validationResponse.addMessage("\nAddress field is empty");
    }

    private void dateOfBirthValidator() {
        if (StringUtils.isEmpty(account.getDateOfBirth()))
            validationResponse.addMessage("\nDateOfBirth field is empty");
        else if (account.getDateOfBirth().length() != 10)
            validationResponse.addMessage("\nWrong dateOfBirth format: must be MM-dd-yyyy ");
        else {
            String pattern = "MM-dd-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            try {
                Date date = simpleDateFormat.parse(account.getDateOfBirth());
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Period period = Period.between(localDate, LocalDate.now());
                if (period.getYears() < 18)
                    validationResponse.addMessage("\nMinimum age must be 18 ");
            } catch (ParseException e) {
                validationResponse.addMessage("\nWrong dateOfBirth format: must be MM-dd-yyyy ");
                e.printStackTrace();
            }
        }
    }

    public void validate() {
        nameValidator();
        usernameValidator();
        emailValidator();
        passwordValidator();
        phoneNumberValidator();
        addressValidator();
        dateOfBirthValidator();

        if (!validationResponse.getMessages().isEmpty())
            validationResponse.setIsValid(true);
    }

    public ValidationResponse getValidationResponse() {
        return validationResponse;
    }
}
