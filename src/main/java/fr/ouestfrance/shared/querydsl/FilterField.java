package fr.ouestfrance.shared.querydsl;

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
     */
    String key() default "";

    /**
     * Operation type of the filter, default is equals
     */
    FilterOperation operation() default FilterOperation.EQ;

    /**
     * Should handle null possible value (isNull or isEqualsTo(XXX))
     */
    boolean orNull() default false;
}