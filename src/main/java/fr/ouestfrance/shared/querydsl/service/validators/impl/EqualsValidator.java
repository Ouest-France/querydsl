package fr.ouestfrance.shared.querydsl.service.validators.impl;

import fr.ouestfrance.shared.querydsl.FilterOperation;
import fr.ouestfrance.shared.querydsl.service.validators.FilterFieldValidator;
import fr.ouestfrance.shared.querydsl.service.validators.ValidatedBy;

/**
 * Validator that handle filter operations giving equalities (EQ, NEQ)
 */
@ValidatedBy({FilterOperation.EQ, FilterOperation.NEQ})
public class EqualsValidator implements FilterFieldValidator {

    @Override
    public boolean validate(Class<?> clazz) {
        return Comparable.class.isAssignableFrom(clazz) || clazz.isPrimitive();
    }

    @Override
    public String message() {
        return "should be applied to Comparables values (LocaleDate, LocaleDateTime, String, ...) or primitives types (boolean, int, long, ...)";
    }
}
