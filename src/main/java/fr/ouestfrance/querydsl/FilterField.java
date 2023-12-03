package fr.ouestfrance.querydsl;

import java.lang.annotation.*;

/**
 * Annotation that allow to declare filter on a field
 * Only on @{@link Target} FIELD
 * Field can handle multiple {@link FilterField} annotations
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(FilterFields.class)
public @interface FilterField {

    /**
     * Key of the filter, if empty the key will be replaced by the fieldName
     *
     * @return key
     */
    String key() default "";

    /**
     * Operation type of the filter, default is equals
     *
     * @return operation type
     */
    FilterOperation operation() default FilterOperation.EQ;

    /**
     * Should handle null possible value (isNull or isEqualsTo(XXX))
     * @return <code>true</code> is should handle null, otherwise <code>false</code>
     */
    boolean orNull() default false;
    /**
     * Group the filter in "or group" every filter with "orGroup" will be joined together
     * @return group name
     */
    String groupName() default "";
}