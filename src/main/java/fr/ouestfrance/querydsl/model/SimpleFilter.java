package fr.ouestfrance.querydsl.model;

import fr.ouestfrance.querydsl.FilterOperation;

/**
 * FilterFieldInfoModel allow to store object representation of FilterField Annotation
 * @param key key of the criteria
 * @param operation operator to apply
 * @param orNull allow the field to be null
 * @param metadata metadata of the field representing type, method accessor and field name
 */
public record SimpleFilter(String key, FilterOperation operation, boolean orNull,
                           FieldMetadata metadata) implements Filter {
}
