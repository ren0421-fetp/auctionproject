package org.fujitsu.training.codes.validator;

import org.fujitsu.training.codes.model.form.LoginForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class LoginFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return LoginForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "username", "username.required", "Username is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "password", "password.required", "Password is required.");
    }
}
