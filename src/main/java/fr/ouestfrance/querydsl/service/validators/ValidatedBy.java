package fr.ouestfrance.querydsl.service.validators;

import fr.ouestfrance.querydsl.FilterOperation;

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
