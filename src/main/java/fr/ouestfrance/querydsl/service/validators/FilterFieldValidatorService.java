package fr.ouestfrance.querydsl.service.validators;

import fr.ouestfrance.querydsl.FilterOperation;
import fr.ouestfrance.querydsl.model.SimpleFilter;
import fr.ouestfrance.querydsl.service.validators.impl.EqualsValidator;
import fr.ouestfrance.querydsl.service.validators.impl.GreaterLessValidator;
import fr.ouestfrance.querydsl.service.validators.impl.InValidator;
import fr.ouestfrance.querydsl.service.validators.impl.LikeValidator;

import java.util.Arrays;
import java.util.List;
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
     * List of validators
     */
    private final List<FilterFieldValidator> validators = List.of(new EqualsValidator(), new GreaterLessValidator(), new InValidator(), new LikeValidator());

    /**
     * Check each filter and build a filter of violations
     *
     * @param filter filter to check
     * @return empty filter if everything is ok, otherwise it returns filter of {@link FilterFieldViolation}
     */
    public Optional<FilterFieldViolation> validate(SimpleFilter filter) {
        return getValidator(filter.operation())
                .filter(x-> !x.validate((Class<?>) filter.metadata().type()))
                .map(x -> new FilterFieldViolation(filter.metadata().name(), "Operation " + filter.operation() + " " + x.message()));
    }

    /**
     * Retrieve the validator from the operation
     *
     * @param operation operation in the FilterField annotation
     * @return Validator for this field
     */
    private Optional<FilterFieldValidator> getValidator(FilterOperation operation) {
        return validators.stream().filter(x -> Arrays.asList(x.getClass().getAnnotation(ValidatedBy.class).value()).contains(operation)).findFirst();
    }
}
