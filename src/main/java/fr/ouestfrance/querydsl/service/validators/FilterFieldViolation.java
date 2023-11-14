package fr.ouestfrance.querydsl.service.validators;

/**
 * FilterFieldViolation represent the error on a field with its declarative message
 * Example:
 * <pre>
 * @FilterField(operation=FilterFieldOperation.IN)
 * private LocalDate startDate
 * ---------------------------------------------
 * result : [fieldName="startDate", message="operation IN should be applied to Collections (Set, List, ...)"]
 * </pre>
 */
public class FilterFieldViolation {

    private final String fieldName;
    private final String message;

    public FilterFieldViolation(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" +
                "fieldName='" + fieldName + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
