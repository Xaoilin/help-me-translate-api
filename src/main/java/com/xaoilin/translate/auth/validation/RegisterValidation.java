package com.xaoilin.translate.auth.validation;

import org.apache.commons.validator.routines.EmailValidator;

public class RegisterValidation {

    private RegisterValidation() {}

    public static boolean isEmailValid(String email) {
        return EmailValidator.getInstance(true).isValid(email);
    }

    public static boolean isPasswordValid(String password) {
        return password.length() >= 6 && password.length() <= 40;
    }
}
