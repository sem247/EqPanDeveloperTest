package com.pancredit.validation;

import com.pancredit.model.ResourceIdentifier;
import com.pancredit.model.TransactionData;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class ApiRequestValidator {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    public void validatePathInfo(String pathInfo) {
        if (pathInfo == null || pathInfo.equals("/")) {
            throw new ValidationException();
        }

        final String[] splits = pathInfo.split("/");

        if (splits.length != 2) {
            throw new ValidationException();
        }

        final ResourceIdentifier resourceIdentifier = new ResourceIdentifier(splits[1]);

        Set<ConstraintViolation<ResourceIdentifier>> violations = validator.validate(resourceIdentifier);
        if (!violations.isEmpty()) {
            throw new ValidationException();
        }
    }

    public void validatePathInfoForPost(String pathInfo) {
        if (pathInfo != null && !"/".equals(pathInfo)) {
            throw new ValidationException();
        }
    }

    public void validatePayload(TransactionData transactionData) {
        Set<ConstraintViolation<TransactionData>> violations = validator.validate(transactionData);
        if (!violations.isEmpty()) {
            throw new ValidationException();
        }
    }

    public void validateResourceIds(String resourceId, TransactionData transactionData) {
        if (!resourceId.equals(transactionData.getId())) {
            throw new ValidationException();
        }
    }
}
