package fr.ouestfrance.querydsl.model;

import fr.ouestfrance.querydsl.FilterOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * FilterFieldInfoModel allow to store object representation of FilterField Annotation
 */
@Getter
@AllArgsConstructor
public class FilterFieldInfoModel {
    /**
     * Key of the filtered data
     */
    private String key;
    /**
     * Operation type (EQ, GTE, LTE, IN, ...)
     */
    private FilterOperation operation;
    /**
     * Specify if the filter should handle the null value
     */
    private boolean orNull;

}
