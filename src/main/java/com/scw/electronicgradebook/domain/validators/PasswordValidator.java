package com.scw.electronicgradebook.domain.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.stream.Stream;

import static java.lang.Character.*;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public void initialize(Password constraintAnnotation) {

    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (password == null)
            return false;

        if (password.length() < 10 || password.length() > 160)
            return false;

        return checkPasswordComplexity(password);
    }

    private boolean checkPasswordComplexity(String password) {
        boolean containsDigit = false;
        boolean containsLowerCase = false;
        boolean containsUpperCase = false;
        boolean containsSpecChar = false;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (isDigit(ch))
                containsDigit = true;
            else if (isLowerCase(ch))
                containsLowerCase = true;
            else if (isUpperCase(ch))
                containsUpperCase = true;
            else
                containsSpecChar = true;
        }

        return checkComplexitySufficiency(containsDigit, containsLowerCase,
                containsSpecChar, containsUpperCase);
    }

    private boolean checkComplexitySufficiency(boolean arg1, boolean arg2,
                                               boolean arg3, boolean arg4) {
        long trueConditions = Stream.of(arg1, arg2, arg3, arg4)
                .filter(condition -> condition)
                .count();

        return trueConditions >= 3;
    }
}
