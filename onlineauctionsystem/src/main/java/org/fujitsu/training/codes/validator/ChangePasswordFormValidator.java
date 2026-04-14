package org.fujitsu.training.codes.validator;

import org.fujitsu.training.codes.model.form.ChangePasswordForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ChangePasswordFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ChangePasswordForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ChangePasswordForm form = (ChangePasswordForm) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentPassword", "current_password.required",
				"Current password is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "new_password.required",
				"New password is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "confirm_password.required",
				"Please confirm the new password.");

		if (form.getNewPassword() != null && form.getNewPassword().length() < 6) {
			errors.rejectValue("newPassword", "new_password.invalid", "New password must be at least 6 characters.");
		}

		if (form.getNewPassword() != null && form.getConfirmPassword() != null
				&& !form.getNewPassword().equals(form.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "confirm_password.mismatch",
					"New password and confirm password do not match.");
		}
	}
}
