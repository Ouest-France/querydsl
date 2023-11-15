package fr.ouestfrance.querydsl.service.validators;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * FilterFieldViolation represent the error on a field with its declarative message
 * Example:
 * <pre>{@code
 * @FilterField(operation=FilterFieldOperation.IN)
 * private LocalDate startDate
 * result : [fieldName="startDate", message="operation IN should be applied to Collections (Set, List, ...)"]
 * }</pre>
 */
@RequiredArgsConstructor
@ToString
public class FilterFieldViolation {
    private final String fieldName;
    private final String message;
}
