package fr.ouestfrance.shared.querydsl.utils;

import fr.ouestfrance.shared.querydsl.service.validators.FilterFieldConstraintException;
import fr.ouestfrance.shared.querydsl.service.validators.FilterFieldViolation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReflectUtils {

    /**
     * Retrieve data from getter
     *
     * @param object search object
     * @param getter getter function
     * @return object return by using object.getFunction()
     */
    public static Object safeGet(Object object, Method getter) {
        try {
            return getter.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    /**
     * Retrieve getter function for a Field
     *
     * @param clazz clazz containing the field
     * @param field declared field
     * @return getter for the field
     */
    public static Method getGetter(Class<?> clazz, Field field) {
        String capitalizedName = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
        String getterPrefix = "get";
        if (field.getType().equals(boolean.class)) {
            getterPrefix = "is";
        }
        try {
            return clazz.getDeclaredMethod(getterPrefix + capitalizedName);
        } catch (NoSuchMethodException e) {
            throw new FilterFieldConstraintException(clazz, List.of(new FilterFieldViolation(field.getName(), "can't find method " + getterPrefix + capitalizedName)));
        }
    }
}
