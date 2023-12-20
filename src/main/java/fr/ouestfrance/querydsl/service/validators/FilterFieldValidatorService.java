package fr.ouestfrance.querydsl.service.validators;

import fr.ouestfrance.querydsl.FilterOperation;
import fr.ouestfrance.querydsl.model.SimpleFilter;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * FilterFieldValidator service allow to check the validity of annotated clazz
 * Some filters operations can't be applied to some types.
 * Valid Examples :
 * <pre>{@code
 *     @FilterField(operation=FilterFieldOperation.IN)
 *     Collection<String> codes;
 *
 *     @FilterField(operation=FilterFieldOperation.GT)
 *     Long quantity;
 * }</pre>
 * <p>
 * Invalid Examples :
 * <pre>{@code
 *     @FilterField(operation=FilterFieldOperation.EQ)
 *     Collection<String> codes; // equals can apply only on Comparable
 *
 *     @FilterField(operation=FilterFieldOperation.IN)
 *     LocalDateTime codes; // in can apply only on Collection
 *
 *     @FilterField(operation=FilterFieldOperation.LIKE)
 *     Long quantity; // Like can apply only on String
 * }</pre>
 */
public class FilterFieldValidatorService {

    /**
     * Map of validators
     */
    private static final Map<Class<? extends FilterFieldValidator>, FilterFieldValidator> VALIDATOR_MAP = new HashMap<>();

    /**
     * Check each filter and build a filter of violations
     *
     * @param filter filter to check
     * @return empty filter if everything is ok, otherwise it returns filter of {@link FilterFieldViolation}
     */
    public Optional<FilterFieldViolation> validate(SimpleFilter filter) {
        FilterFieldValidator validator = getValidator(filter.operation());
        if (validator.validate(filter.field().getType())) {
            return Optional.empty();
        } else {
            return Optional.of(new FilterFieldViolation(filter.field().getName(), "Operation " + filter.operation() + " " + validator.message()));
        }
    }

    /**
     * Retrieve the validator from the operation
     *
     * @param operation operation in the FilterField annotation
     * @return Validator for this field
     */
    private FilterFieldValidator getValidator(Class<? extends FilterOperation> operation) {
        ValidatedBy validator = operation.getAnnotation(ValidatedBy.class);
        if (validator == null) {
            throw new IllegalStateException("Operation " + operation + " should be annotated with @ValidatedBy");
        }
        return VALIDATOR_MAP.computeIfAbsent(validator.value(), x -> {
            try {
                return x.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException e) {
                throw new IllegalStateException("Validator " + x + " should have a default constructor", e);
            }
        });
    }
}
