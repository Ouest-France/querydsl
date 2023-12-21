package fr.ouestfrance.querydsl.model;

import fr.ouestfrance.querydsl.FilterOperation;

import java.lang.reflect.Field;

/**
 * FilterFieldInfoModel allow to store object representation of FilterField Annotation
 *
 * @param key       key of the criteria
 * @param operation operator to apply
 * @param orNull    allow the field to be null
 * @param field     field of the field representing type, method accessor and field name
 */
public record SimpleFilter(String key, Class<? extends FilterOperation> operation, boolean orNull,
                           Field field) implements Filter {
}
