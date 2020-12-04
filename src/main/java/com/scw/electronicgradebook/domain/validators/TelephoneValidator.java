package com.scw.electronicgradebook.domain.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TelephoneValidator implements ConstraintValidator<Telephone, String> {

    @Override
    public boolean isValid(String telephone, ConstraintValidatorContext constraintValidatorContext) {
        if (telephone == null)
            return true;

        return telephone.matches("[0-9]{6,10}");
    }

}
