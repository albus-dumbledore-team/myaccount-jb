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

    private ValidationResponse validationResponse;

    private void nameValidator(String name) {
        if (StringUtils.isEmpty(name)) {
            this.validationResponse.addMessage("\nName field is empty");
        } else {
            final String nameExpression = "^([A-Z][a-z]*((\\s)))+[A-Z][a-z]*$";
            if (!name.matches(nameExpression)) {
                this.validationResponse.addMessage("\nWrong name format: must contain only letters and must be \"FirstName LastName\"  ");
            }
        }
    }

    private void emailValidator(String email) {
        if (StringUtils.isEmpty(email)) {
            this.validationResponse.addMessage("\nEmail field is empty");
        } else {
            final String emailExpression = "^(.+)@(.+)$";
            if (!email.matches(emailExpression)) {
                this.validationResponse.addMessage("\nWrong email format");
            }
        }
    }

    private void usernameValidator(String username) {
        if (StringUtils.isEmpty(username)) {
            this.validationResponse.addMessage("\nUsername field is empty");
        } else {
            final String userNameExpression = "[a-zA-Z0-9]*$";
            if (!username.matches(userNameExpression)) {
                this.validationResponse.addMessage("\nWrong username format: must contain only digits and letter ");
            }
        }
    }

    private void passwordValidator(String password) {
        if (StringUtils.isEmpty(password)) {
            this.validationResponse.addMessage("\nPassword field is empty");
        }
    }

    private void phoneNumberValidator(String phoneNumber) {
        if (StringUtils.isEmpty(phoneNumber)) {
            this.validationResponse.addMessage("\nPhoneNumber field is empty");
        } else if (phoneNumber.length() != 10) {
            this.validationResponse.addMessage("\nWrong phoneNumber format: must have 10 digits ");
        } else {
            boolean match = Arrays.asList(phoneNumber.split(""))
                    .stream()
                    .allMatch(str -> str.matches("\\d+"));
            if (!match) {
                this.validationResponse.addMessage("\nWrong phoneNumber format: must contain only digits ");
            }
        }
    }

    private void addressValidator(String address) {
        if (StringUtils.isEmpty(address)) {
            this.validationResponse.addMessage("\nAddress field is empty");
        }
    }

    private void dateOfBirthValidator(String dateOfBirth) {
        if (StringUtils.isEmpty(dateOfBirth)) {
            this.validationResponse.addMessage("\nDateOfBirth field is empty");
        } else if (dateOfBirth.length() != 10) {
            this.validationResponse.addMessage("\nWrong dateOfBirth format: must be MM-dd-yyyy ");
        } else {
            String pattern = "MM-dd-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            try {
                Date date = simpleDateFormat.parse(dateOfBirth);
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Period period = Period.between(localDate, LocalDate.now());
                if (period.getYears() < 18) {
                    this.validationResponse.addMessage("\nMinimum age must be 18 ");
                }
            } catch (ParseException e) {
                this.validationResponse.addMessage("\nWrong dateOfBirth format: must be MM-dd-yyyy ");
                e.printStackTrace();
            }
        }
    }

    public ValidationResponse validate(Account account) {

        this.validationResponse = new ValidationResponse();

        nameValidator(account.getName());
        usernameValidator(account.getUsername());
        emailValidator(account.getEmail());
        passwordValidator(account.getPassword());
        phoneNumberValidator(account.getPhoneNumber());
        addressValidator(account.getAddress());
        dateOfBirthValidator(account.getDateOfBirth());

        if (!this.validationResponse.getMessages().isEmpty()) {
            this.validationResponse.setIsValid(false);
        }

        return this.validationResponse;
    }

}
