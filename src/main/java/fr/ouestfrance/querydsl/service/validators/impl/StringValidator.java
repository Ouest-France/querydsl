package fr.ouestfrance.querydsl.service.validators.impl;

import fr.ouestfrance.querydsl.service.validators.FilterFieldValidator;
import lombok.NoArgsConstructor;

/**
 * Validator that handle filter operations giving like (LIKE)
 */
@NoArgsConstructor
public class StringValidator implements FilterFieldValidator {
    @Override
    public boolean validate(Class<?> clazz) {
        return String.class.isAssignableFrom(clazz);
    }

    @Override
    public String message() {
        return "should be applied to String";
    }
}
