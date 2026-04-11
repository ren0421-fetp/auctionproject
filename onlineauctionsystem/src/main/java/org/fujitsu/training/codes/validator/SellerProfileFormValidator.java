package org.fujitsu.training.codes.validator;

import org.fujitsu.training.codes.model.form.SellerProfileForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SellerProfileFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return SellerProfileForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SellerProfileForm form = (SellerProfileForm) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "first_name.required", "First name is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "last_name.required", "Last name is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "gender.required", "Please select a gender.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "address.required", "Address is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.required", "Email address is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactNo", "contact.required", "Contact number is required.");

        if (form.getCountryId() == null) {
            errors.rejectValue("countryId", "country.required", "Please select a country.");
        }
        if (form.getStateId() == null) {
            errors.rejectValue("stateId", "state.required", "Please select a state.");
        }
        if (form.getCityId() == null) {
            errors.rejectValue("cityId", "city.required", "Please select a city.");
        }
        if (form.getEmail() != null && !form.getEmail().contains("@")) {
            errors.rejectValue("email", "email.invalid", "Please enter a valid email address.");
        }
        if (form.getContactNo() != null && !form.getContactNo().matches("\\d{7,20}")) {
            errors.rejectValue("contactNo", "contact.invalid", "Contact number must be numeric (7-20 digits).");
        }
    }
}
