package fr.ouestfrance.querydsl.service.validators;

import lombok.Getter;

import java.util.List;

/**
 * Constraint exception on annotations
 */
@Getter
public class FilterFieldConstraintException extends RuntimeException {

    /**
     * List of violations
     */
    private final transient List<FilterFieldViolation> violations;
    /**
     * Class of the bean scanned
     */
    private final Class<?> clazz;

    /**
     * Constructor
     * @param clazz clazz of bean scanned
     * @param violations list of violations
     */
    public FilterFieldConstraintException(Class<?> clazz, List<FilterFieldViolation> violations) {
        super("Class " + clazz + " can't be validated cause violations : " + String.join(",", violations.stream().map(FilterFieldViolation::toString).toList()));
        this.violations = violations;
        this.clazz = clazz;
    }
}
