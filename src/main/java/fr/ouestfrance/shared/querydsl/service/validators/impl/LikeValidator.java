package fr.ouestfrance.shared.querydsl.service.validators.impl;

import fr.ouestfrance.shared.querydsl.FilterOperation;
import fr.ouestfrance.shared.querydsl.service.validators.FilterFieldValidator;
import fr.ouestfrance.shared.querydsl.service.validators.ValidatedBy;

/**
 * Validator that handle filter operations giving like (LIKE)
 */
@ValidatedBy({FilterOperation.LIKE})
public class LikeValidator implements FilterFieldValidator {
    @Override
    public boolean validate(Class<?> clazz) {
        return String.class.isAssignableFrom(clazz);
    }

    @Override
    public String message() {
        return "should be applied to String";
    }
}
