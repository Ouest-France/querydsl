package fr.ouestfrance.querydsl.model;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * FieldInformation class represent metadata on declared field in a class
 *
 * @param name   Name of the field
 * @param type   Return declared type of the field
 * @param getter Getter method to access data
 */
public record FieldMetadata(String name, Type type, Method getter) {

}
