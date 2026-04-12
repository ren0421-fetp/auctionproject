package org.fujitsu.training.codes.validator;

import org.fujitsu.training.codes.model.form.StateForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class StateFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return StateForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StateForm form = (StateForm) target;

        if (form.getCountryId() == null) {
            errors.rejectValue("countryId", "country.required", "Country is required.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stateName",
                "state_name.required", "State name is required.");
    }
}
