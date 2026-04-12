package org.fujitsu.training.codes.validator;

import org.fujitsu.training.codes.model.form.FeedbackForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class FeedbackFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return FeedbackForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName",
                "firstname.required", "First name is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email",
                "email.required", "Email is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contact",
                "contact.required", "Contact is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subject",
                "subject.required", "Subject is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "msg",
                "msg.required", "Message is required.");
    }
}
