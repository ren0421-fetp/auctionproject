package org.fujitsu.training.codes.validator;

import org.fujitsu.training.codes.model.form.CategoryForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CategoryFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return CategoryForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CategoryForm form = (CategoryForm) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "catName", "cat_name.required", "Category name is required.");

		if (form.getCatName() != null && form.getCatName().trim().length() > 50) {
			errors.rejectValue("catName", "cat_name.length", "Category name must not exceed 50 characters.");
		}
	}
}
