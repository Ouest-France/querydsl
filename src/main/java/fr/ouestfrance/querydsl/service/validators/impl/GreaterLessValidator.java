package fr.ouestfrance.querydsl.service.validators.impl;

import fr.ouestfrance.querydsl.FilterOperation;
import fr.ouestfrance.querydsl.service.validators.FilterFieldValidator;
import fr.ouestfrance.querydsl.service.validators.ValidatedBy;
import lombok.NoArgsConstructor;

/**
 * Validator that handle filter operations comparable (GT,GTE, LT, LTE)
 */
@ValidatedBy({FilterOperation.GT, FilterOperation.GTE, FilterOperation.LT, FilterOperation.LTE})
@NoArgsConstructor
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
