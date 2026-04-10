package org.fujitsu.training.codes.validator;

import org.fujitsu.training.codes.model.form.RegistrationForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RegistrationFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegistrationForm form = (RegistrationForm) target;

        // Using the 4-argument version: (errors, field, errorCode, defaultMessage)
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "first_name.required", "First name is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "last_name.required", "Last name is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "gender.required", "Please select a gender.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "address.required", "Address is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.required", "Username is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required", "Password is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "confirm_password.required", "Please confirm your password.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.required", "Email address is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactNo", "contact.required", "Contact number is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userType", "user_type.required", "Please select an account type.");

        // For manual rejectValue, we use the 3-argument version: (field, errorCode, defaultMessage)
        if (form.getCountryId() == null) {
            errors.rejectValue("countryId", "country.required", "Please select a country.");
        }
        if (form.getStateId() == null) {
            errors.rejectValue("stateId", "state.required", "Please select a state.");
        }
        if (form.getCityId() == null) {
            errors.rejectValue("cityId", "city.required", "Please select a city.");
        }
        
        if (form.getPassword() != null && form.getConfirmPassword() != null
                && !form.getPassword().equals(form.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "confirm_password.mismatch", "Passwords do not match.");
        }
        
        if (form.getUsername() != null && form.getUsername().length() > 25) {
            errors.rejectValue("username", "username.length", "Username must be at most 25 characters.");
        }
        
        if (form.getEmail() != null && !form.getEmail().contains("@")) {
            errors.rejectValue("email", "email.invalid", "Please enter a valid email address.");
        }
        
        if (form.getContactNo() != null && !form.getContactNo().matches("\\d{7,20}")) {
            errors.rejectValue("contactNo", "contact.invalid", "Contact number must be numeric (7-20 digits).");
        }
        
        if (form.getUserType() != null
                && !form.getUserType().equals("seller")
                && !form.getUserType().equals("bidder")) {
            errors.rejectValue("userType", "user_type.invalid", "Invalid account type selected.");
        }
    }
}