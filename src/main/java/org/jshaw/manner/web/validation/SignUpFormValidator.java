package org.jshaw.manner.web.validation;

import org.jshaw.manner.domain.SignUpForm;
import org.jshaw.manner.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Jason on 4/25/15.
 */
@Component
public class SignUpFormValidator implements Validator {

    private static final Logger logger = LoggerFactory.getLogger(SignUpFormValidator.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return SignUpForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        SignUpForm signUpForm = (SignUpForm) o;

        if (!signUpForm.getPassword().equals(signUpForm.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "confirmPassword.not.match");
        }

        if (userRepository.findByUsername(signUpForm.getUsername()) != null) {
            errors.rejectValue("username", "username.exists");
        }

        if (userRepository.findByEmail(signUpForm.getEmail()) != null) {
            errors.rejectValue("email", "email.exists");
        }
    }
}
