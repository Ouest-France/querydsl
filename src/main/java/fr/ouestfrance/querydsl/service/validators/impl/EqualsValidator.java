package fr.ouestfrance.querydsl.service.validators.impl;

import fr.ouestfrance.querydsl.FilterOperation;
import fr.ouestfrance.querydsl.service.validators.FilterFieldValidator;
import fr.ouestfrance.querydsl.service.validators.ValidatedBy;
import lombok.NoArgsConstructor;

/**
 * Validator that handle filter operations giving equalities (EQ, NEQ)
 */
@ValidatedBy({FilterOperation.EQ, FilterOperation.NEQ})
@NoArgsConstructor
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
