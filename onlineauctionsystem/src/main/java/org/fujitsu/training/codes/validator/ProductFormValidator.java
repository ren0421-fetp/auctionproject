package org.fujitsu.training.codes.validator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.fujitsu.training.codes.model.form.ProductForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProductFormValidator implements Validator {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

	@Override
	public boolean supports(Class<?> clazz) {
		return ProductForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ProductForm form = (ProductForm) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productName", "product_name.required",
				"Product name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "description.required",
				"Description is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "status", "status.required", "Status is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "start_date.required",
				"Start date is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDate", "end_date.required", "End date is required.");

		if (form.getCatId() == null) {
			errors.rejectValue("catId", "category.required", "Please select a category.");
		}

		if (form.getMinBidPrice() == null) {
			errors.rejectValue("minBidPrice", "min_bid.required", "Minimum bid price is required.");
		} else if (form.getMinBidPrice().compareTo(BigDecimal.ZERO) <= 0) {
			errors.rejectValue("minBidPrice", "min_bid.invalid", "Minimum bid price must be greater than zero.");
		}

		boolean addMode = form.getProductId() == null;
		boolean hasExistingPhoto = form.getCurrentPhotoPath() != null && !form.getCurrentPhotoPath().isBlank();
		if (addMode && (form.getPhotoFile() == null || form.getPhotoFile().isEmpty()) && !hasExistingPhoto) {
			errors.rejectValue("photoFile", "photo.required", "Product photo is required.");
		}

		if (form.getStatus() != null && !form.getStatus().equals("open") && !form.getStatus().equals("closed")) {
			errors.rejectValue("status", "status.invalid", "Status must be open or closed.");
		}

		if (form.getStartDate() != null && !form.getStartDate().isBlank() && form.getEndDate() != null
				&& !form.getEndDate().isBlank()) {
			try {
				LocalDateTime start = LocalDateTime.parse(form.getStartDate(), FORMATTER);
				LocalDateTime end = LocalDateTime.parse(form.getEndDate(), FORMATTER);

				if (!end.isAfter(start)) {
					errors.rejectValue("endDate", "end_date.invalid", "End date must be after start date.");
				}
			} catch (DateTimeParseException ex) {
				errors.rejectValue("startDate", "date.invalid",
						"Invalid date format. Please use the provided date picker.");
			}
		}
	}
}
