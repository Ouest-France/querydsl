package fr.ouestfrance.shared.querydsl.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

/**
 * FilterFieldModel is a model representation of annotations filterFields
 * It allows to store field representation with its annotations
 */
@AllArgsConstructor
@Getter
public class FilterFieldModel {

    /**
     * Name of the field in the clazz
     */
    private String name;
    /**
     * List of annotations (see FilterField) declared over the field
     */
    private List<FilterFieldInfoModel> filterFields;
    /**
     * Getter method to access data
     */
    private Method getter;
    /**
     * Return declared type of the field
     */
    private Type type;

}
