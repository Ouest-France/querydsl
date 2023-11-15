package fr.ouestfrance.querydsl.service.validators.impl;

import fr.ouestfrance.querydsl.FilterOperation;
import fr.ouestfrance.querydsl.service.validators.FilterFieldValidator;
import fr.ouestfrance.querydsl.service.validators.ValidatedBy;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * Validator that handle filter operations containing (IN, NOT_IN)
 */
@ValidatedBy({FilterOperation.IN, FilterOperation.NOT_IN})
@NoArgsConstructor
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
