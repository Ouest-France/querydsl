package fr.ouestfrance.querydsl;

import java.lang.annotation.*;

/**
 * Repeatable annotation of {@link FilterField}
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)

public @interface FilterFields {

    /**
     * List of filterField, using value allow to avoid attribute name
     * @return list of filterField
     */
    FilterField[] value() default {};
}