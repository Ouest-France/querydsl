package fr.ouestfrance.shared.querydsl.service.validators.impl;

import fr.ouestfrance.shared.querydsl.FilterOperation;
import fr.ouestfrance.shared.querydsl.service.validators.FilterFieldValidator;
import fr.ouestfrance.shared.querydsl.service.validators.ValidatedBy;

/**
 * Validator that handle filter operations comparable (GT,GTE, LT, LTE)
 */
@ValidatedBy({FilterOperation.GT, FilterOperation.GTE, FilterOperation.LT, FilterOperation.LTE})
public class GreaterLessValidator implements FilterFieldValidator {

    @Override
    public boolean validate(Class<?> clazz) {
        return Comparable.class.isAssignableFrom(clazz);
    }

    @Override
    public String message() {
        return "should be applied to Comparables values (LocaleDate, LocaleDateTime, String, ...)";
    }
}
