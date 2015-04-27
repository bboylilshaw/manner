package org.jshaw.manner.web.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Jason on 4/25/15.
 */
public class SignUpFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {

    }
}
