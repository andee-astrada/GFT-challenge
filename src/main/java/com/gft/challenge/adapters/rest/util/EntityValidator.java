package com.gft.challenge.util;

import com.gft.challenge.exception.FieldValidationException;
import com.gft.challenge.adapters.rest.dto.SearchCriteria;
import org.apache.logging.log4j.util.Strings;

public class EntityValidator {

    private EntityValidator() {
        throw new IllegalStateException("Utility class");
    }

    public static void validate(SearchCriteria criteria) throws FieldValidationException {
        StringBuilder errorMessage = new StringBuilder();

        if (Strings.isBlank(criteria.getBrandId()))
            errorMessage.append("Field brand_id is required. ");
        if (Strings.isBlank(criteria.getProductId()))
            errorMessage.append("Field product_id is required. ");
        if (criteria.getRequestDate() == null)
            errorMessage.append("Field request_date is required. ");

        if (!errorMessage.isEmpty())
            throw new FieldValidationException("Errors occurred when validating the search criteria: " + errorMessage);
    }
}
