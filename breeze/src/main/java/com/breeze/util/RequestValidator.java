package com.breeze.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RequestValidator {

    private Validator validator;

    public RequestValidator(Validator validator) {
        if (validator == null) {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            this.validator = factory.getValidator();
        } else {
            this.validator = validator;
        }
    }

    public <T> void validate(T request) {
        Set<ConstraintViolation<T>> violations = validator.validate(request);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed for request: ");
            for (ConstraintViolation<T> violation : violations) {
                errorMessage.append(violation.getPropertyPath())
                        .append(" ")
                        .append(violation.getMessage())
                        .append("; ");
            }
            throw new IllegalArgumentException(errorMessage.toString());
        }
    }
}
