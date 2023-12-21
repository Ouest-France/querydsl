package fr.ouestfrance.querydsl.service.validators;

import java.lang.annotation.*;

/**
 * Annotation that allow to declare FilterFieldValidator
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Target(ElementType.TYPE)
public @interface ValidatedBy {
    /**
     * Opération to handle
     *
     * @return operation
     */
    Class<? extends FilterFieldValidator> value();
}
