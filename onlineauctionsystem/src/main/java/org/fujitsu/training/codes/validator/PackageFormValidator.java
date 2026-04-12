package org.fujitsu.training.codes.validator;

import java.math.BigDecimal;

import org.fujitsu.training.codes.model.form.PackageForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PackageFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PackageForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PackageForm form = (PackageForm) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "packageName",
                "package_name.required", "Package name is required.");

        if (form.getPackageName() != null && form.getPackageName().trim().length() > 100) {
            errors.rejectValue("packageName", "package_name.length",
                    "Package name must not exceed 100 characters.");
        }

        if (form.getPackagePrice() == null) {
            errors.rejectValue("packagePrice", "package_price.required",
                    "Package price is required.");
        } else if (form.getPackagePrice().compareTo(BigDecimal.ZERO) < 0) {
            errors.rejectValue("packagePrice", "package_price.invalid",
                    "Package price must not be negative.");
        }

        if (form.getAllowedBidCount() == null) {
            errors.rejectValue("allowedBidCount", "allowed_bid_count.required",
                    "Allowed bid count is required.");
        } else if (form.getAllowedBidCount() < 0) {
            errors.rejectValue("allowedBidCount", "allowed_bid_count.invalid",
                    "Allowed bid count must not be negative.");
        }
        
        boolean addMode = form.getPackageId() == null;
        boolean hasExistingPhoto = form.getCurrentPhotoPath() != null && !form.getCurrentPhotoPath().isBlank();

        if (addMode && (form.getPhotoFile() == null || form.getPhotoFile().isEmpty()) && !hasExistingPhoto) {
            errors.rejectValue("photoFile", "photo.required", "Package photo is required.");
        }

    }
}
