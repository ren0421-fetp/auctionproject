package org.fujitsu.training.codes.validator;

import org.fujitsu.training.codes.model.form.CityForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CityFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return CityForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CityForm form = (CityForm) target;

		if (form.getStateId() == null) {
			errors.rejectValue("stateId", "state.required", "State is required.");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cityName", "city_name.required", "City name is required.");
	}
}
