package org.fujitsu.training.codes.validator;

import java.math.BigDecimal;

import org.fujitsu.training.codes.model.form.BidForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BidFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return BidForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BidForm form = (BidForm) target;

        if (form.getProductId() == null) {
            errors.rejectValue("productId", "product.required", "Product is required.");
        }

        if (form.getBidPrice() == null) {
            errors.rejectValue("bidPrice", "bid.required", "Bid price is required.");
        } else if (form.getBidPrice().compareTo(BigDecimal.ZERO) <= 0) {
            errors.rejectValue("bidPrice", "bid.invalid", "Bid price must be greater than zero.");
        }
    }
}
