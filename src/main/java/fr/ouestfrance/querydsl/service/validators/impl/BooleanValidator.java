package fr.ouestfrance.querydsl.service.validators.impl;

import fr.ouestfrance.querydsl.service.validators.FilterFieldValidator;
import lombok.NoArgsConstructor;

/**
 * Validator that handle filter on Boolean
 */
@NoArgsConstructor
public class BooleanValidator implements FilterFieldValidator {
    @Override
    public boolean validate(Class<?> clazz) {
        return Boolean.class.isAssignableFrom(clazz);
    }

    @Override
    public String message() {
        return "should be applied to Boolean";
    }
}
