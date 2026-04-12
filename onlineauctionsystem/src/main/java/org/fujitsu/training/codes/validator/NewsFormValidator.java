package org.fujitsu.training.codes.validator;

import org.fujitsu.training.codes.model.form.NewsForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class NewsFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return NewsForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newsTitle",
                "news_title.required", "News title is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newsContent",
                "news_content.required", "News content is required.");
    }
}
