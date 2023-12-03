package fr.ouestfrance.querydsl.service.validators;

/**
 * FilterFieldViolation represent the error on a field with its declarative message
 * Example:
 * <pre>{@code
 * @FilterField(operation=FilterFieldOperation.IN)
 * private LocalDate startDate
 *
 * // result : [fieldName="startDate", message="operation IN should be applied to Collections (Set, List, ...)"]
 * }</pre>
 *
 * @param fieldName name of the field
 * @param message error message
 */
public record FilterFieldViolation(String fieldName, String message) {
}
