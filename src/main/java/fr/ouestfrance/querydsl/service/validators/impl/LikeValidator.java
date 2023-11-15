package fr.ouestfrance.querydsl.service.validators.impl;

import fr.ouestfrance.querydsl.FilterOperation;
import fr.ouestfrance.querydsl.service.validators.FilterFieldValidator;
import fr.ouestfrance.querydsl.service.validators.ValidatedBy;
import lombok.NoArgsConstructor;

/**
 * Validator that handle filter operations giving like (LIKE)
 */
@ValidatedBy({FilterOperation.LIKE})
@NoArgsConstructor
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
