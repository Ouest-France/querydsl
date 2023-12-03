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
     *
     * @return filter fields
     */
    FilterField[] value() default {};

    /**
     * Group the filter in "or group" every filter with "orGroup" will be joined together
     *
     * @return group name
     */
    String groupName() default "";
}