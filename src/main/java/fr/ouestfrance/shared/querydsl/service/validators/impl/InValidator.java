package fr.ouestfrance.shared.querydsl.service.validators.impl;

import fr.ouestfrance.shared.querydsl.FilterOperation;
import fr.ouestfrance.shared.querydsl.service.validators.FilterFieldValidator;
import fr.ouestfrance.shared.querydsl.service.validators.ValidatedBy;

import java.util.Collection;

/**
 * Validator that handle filter operations containing (IN, NOT_IN)
 */
@ValidatedBy({FilterOperation.IN, FilterOperation.NOT_IN})
public class InValidator implements FilterFieldValidator {

    @Override
    public boolean validate(Class<?> clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }

    @Override
    public String message() {
        return "should be applied to Collections (Set, List, ...)";
    }
}
