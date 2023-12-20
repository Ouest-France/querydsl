package fr.ouestfrance.querydsl.service.validators.impl;

import fr.ouestfrance.querydsl.service.validators.FilterFieldValidator;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * Validator that handle filter operations containing (IN, NOT_IN)
 */
@NoArgsConstructor
public class CollectionValidator implements FilterFieldValidator {

    @Override
    public boolean validate(Class<?> clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }

    @Override
    public String message() {
        return "should be applied to Collections (Set, List, ...)";
    }
}
