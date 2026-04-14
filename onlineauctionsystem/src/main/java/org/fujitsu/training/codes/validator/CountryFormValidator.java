package org.fujitsu.training.codes.validator;

import org.fujitsu.training.codes.model.form.CountryForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CountryFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return CountryForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "countryName", "country_name.required",
				"Country name is required.");
	}
}
