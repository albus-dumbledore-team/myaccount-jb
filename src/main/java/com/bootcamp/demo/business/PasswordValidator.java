package com.bootcamp.demo.business;

public class PasswordValidator {

    public static boolean validate(String password) throws Exception {
        StringBuilder errorBuilder = new StringBuilder();
        if(password.length() < 8)
            errorBuilder.append("Password length must be at least 8\n");

        String expression = "(?=.*[a-z])";
        if(!password.matches(expression))
            errorBuilder.append("Password must contain at least one lowercase character\n");

        expression = "(?=.*[A-Z])";
        if(!password.matches(expression))
            errorBuilder.append("Password must contain at least one uppercase character\n");

        expression = "(?=.*\\d)";
        if(!password.matches(expression))
            errorBuilder.append("Password must contain at least one digit\n");

        expression = "(?=.*\\W])";
        if(!password.matches(expression))
            errorBuilder.append("Password must contain at least one symbol\n");
        String errorString = errorBuilder.toString();
        if(errorString.isEmpty())
            return true;
        else throw new Exception(errorString);
    }
}
