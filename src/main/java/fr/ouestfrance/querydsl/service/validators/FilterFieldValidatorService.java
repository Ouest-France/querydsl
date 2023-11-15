package fr.ouestfrance.querydsl.service.validators;

import fr.ouestfrance.querydsl.FilterOperation;
import fr.ouestfrance.querydsl.model.FilterFieldModel;
import fr.ouestfrance.querydsl.service.validators.impl.EqualsValidator;
import fr.ouestfrance.querydsl.service.validators.impl.GreaterLessValidator;
import fr.ouestfrance.querydsl.service.validators.impl.InValidator;
import fr.ouestfrance.querydsl.service.validators.impl.LikeValidator;

import java.util.ArrayList;
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
     * Check each filterFieldModel and build a list of violations
     *
     * @param list list to check
     * @return empty list if everything is ok, otherwise it returns list of {@link FilterFieldViolation}
     */
    public List<FilterFieldViolation> validate(List<FilterFieldModel> list) {
        List<FilterFieldViolation> violations = new ArrayList<>();
        list.forEach(x -> x.getFilterFields().forEach(y ->
                getValidator(y.getOperation()).ifPresent(val -> {
                            if (!val.validate((Class<?>) x.getType())) {
                                violations.add(new FilterFieldViolation(x.getName(), "Operation " + y.getOperation() + " " + val.message()));
                            }
                        }
                )));
        return violations;
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
