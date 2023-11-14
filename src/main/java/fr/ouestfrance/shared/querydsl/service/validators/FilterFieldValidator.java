package fr.ouestfrance.shared.querydsl.service.validators;

/**
 * Validator interface
 */
public interface FilterFieldValidator {

    /**
     * Error message of the validator
     *
     * @return message that gives the rules for the validator
     */
    String message();

    /**
     * Validate the type
     *
     * @param clazz type to validate
     * @return <code>true</code> is the clazz is valid, otherwise <code>false</code>
     */
    boolean validate(Class<?> clazz);
}
