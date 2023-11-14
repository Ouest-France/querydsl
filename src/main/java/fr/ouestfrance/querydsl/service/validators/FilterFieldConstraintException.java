package fr.ouestfrance.querydsl.service.validators;

import lombok.Getter;

import java.util.List;

/**
 * Constraint exception on annotations
 */
@Getter
public class FilterFieldConstraintException extends RuntimeException {

    private final List<FilterFieldViolation> violations;
    private final Class<?> clazz;

    public FilterFieldConstraintException(Class<?> clazz, List<FilterFieldViolation> violations) {
        super("Class " + clazz + " can't be validated cause violations : " + String.join(",", violations.stream().map(FilterFieldViolation::toString).toList()));
        this.violations = violations;
        this.clazz = clazz;
    }
}
