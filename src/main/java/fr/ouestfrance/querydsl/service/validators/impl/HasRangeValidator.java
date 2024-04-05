package fr.ouestfrance.querydsl.service.validators.impl;

import fr.ouestfrance.querydsl.service.ext.HasRange;
import fr.ouestfrance.querydsl.service.validators.FilterFieldValidator;
import lombok.NoArgsConstructor;

/**
 * Validator that handle filter on HasRange
 */
@NoArgsConstructor
public class HasRangeValidator implements FilterFieldValidator {
    @Override
    public boolean validate(Class<?> clazz) {
        return HasRange.class.isAssignableFrom(clazz);
    }

    @Override
    public String message() {
        return "should be applied to HasRange";
    }
}
