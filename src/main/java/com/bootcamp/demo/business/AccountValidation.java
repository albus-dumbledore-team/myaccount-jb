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

    private void nameValidator(String name, ValidationResponse validationResponse) {
        if (StringUtils.isEmpty(name)) {
            validationResponse.addMessage("\nName field is empty");
        } else {
            final String nameExpression = "^([A-Z][a-z]*((\\s)))+[A-Z][a-z]*$";
            if (!name.matches(nameExpression)) {
                validationResponse.addMessage("\nWrong name format: must contain only letters and must be \"FirstName LastName\"  ");
            }
        }
    }

    private void emailValidator(String email, ValidationResponse validationResponse) {
        if (StringUtils.isEmpty(email)) {
            validationResponse.addMessage("\nEmail field is empty");
        } else {
            final String emailExpression = "^(.+)@(.+)$";
            if (!email.matches(emailExpression)) {
                validationResponse.addMessage("\nWrong email format");
            }
        }
    }

    private void usernameValidator(String username, ValidationResponse validationResponse) {
        if (StringUtils.isEmpty(username)) {
            validationResponse.addMessage("\nUsername field is empty");
        } else {
            final String userNameExpression = "[a-zA-Z0-9]*$";
            if (!username.matches(userNameExpression)) {
                validationResponse.addMessage("\nWrong username format: must contain only digits and letter ");
            }
        }
    }

    private void passwordValidator(String password, ValidationResponse validationResponse) {
        if (StringUtils.isEmpty(password)) {
            validationResponse.addMessage("\nPassword field is empty");
        }
    }

    private void phoneNumberValidator(String phoneNumber, ValidationResponse validationResponse) {
        if (StringUtils.isEmpty(phoneNumber)) {
            validationResponse.addMessage("\nPhoneNumber field is empty");
        } else if (phoneNumber.length() != 10) {
            validationResponse.addMessage("\nWrong phoneNumber format: must have 10 digits ");
        } else {
            boolean match = Arrays.asList(phoneNumber.split(""))
                    .stream()
                    .allMatch(str -> str.matches("\\d+"));
            if (!match) {
                validationResponse.addMessage("\nWrong phoneNumber format: must contain only digits ");
            }
        }
    }

    private void addressValidator(String address, ValidationResponse validationResponse) {
        if (StringUtils.isEmpty(address)) {
            validationResponse.addMessage("\nAddress field is empty");
        }
    }

    private void dateOfBirthValidator(String dateOfBirth, ValidationResponse validationResponse) {
        if (StringUtils.isEmpty(dateOfBirth)) {
            validationResponse.addMessage("\nDateOfBirth field is empty");
        } else if (dateOfBirth.length() != 10) {
            validationResponse.addMessage("\nWrong dateOfBirth format: must be MM-dd-yyyy ");
        } else {
            String pattern = "MM-dd-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            try {
                Date date = simpleDateFormat.parse(dateOfBirth);
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Period period = Period.between(localDate, LocalDate.now());
                if (period.getYears() < 18) {
                    validationResponse.addMessage("\nMinimum age must be 18 ");
                }
            } catch (ParseException e) {
                validationResponse.addMessage("\nWrong dateOfBirth format: must be MM-dd-yyyy ");
                e.printStackTrace();
            }
        }
    }

    public ValidationResponse validate(Account account) {

        ValidationResponse validationResponse = new ValidationResponse();

        nameValidator(account.getName(), validationResponse);
        usernameValidator(account.getUsername(), validationResponse);
        emailValidator(account.getEmail(), validationResponse);
        passwordValidator(account.getPassword(), validationResponse);
        phoneNumberValidator(account.getPhoneNumber(), validationResponse);
        addressValidator(account.getAddress(), validationResponse);
        dateOfBirthValidator(account.getDateOfBirth(), validationResponse);

        if (!validationResponse.getMessages().isEmpty()) {
            validationResponse.setIsValid(false);
        }

        return validationResponse;
    }

}
