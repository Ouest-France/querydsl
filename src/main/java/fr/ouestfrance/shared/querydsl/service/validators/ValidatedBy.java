package fr.ouestfrance.shared.querydsl.service.validators;

import fr.ouestfrance.shared.querydsl.FilterOperation;

import java.lang.annotation.*;

/**
 * Annotation that allow to declare FilterFieldValidator
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Target(ElementType.TYPE)
public @interface ValidatedBy {
    /**Opération*/
    FilterOperation[] value();
}
