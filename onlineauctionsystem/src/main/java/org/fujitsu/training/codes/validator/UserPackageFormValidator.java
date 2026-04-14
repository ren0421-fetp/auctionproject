package org.fujitsu.training.codes.validator;

import org.fujitsu.training.codes.model.form.UserPackageForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserPackageFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserPackageForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserPackageForm form = (UserPackageForm) target;

		if (form.getUsername() == null || form.getUsername().isBlank()) {
			errors.rejectValue("username", "username.required", "Bidder is required.");
		}

		if (form.getPackageId() == null) {
			errors.rejectValue("packageId", "package.required", "Package is required.");
		}
	}
}
