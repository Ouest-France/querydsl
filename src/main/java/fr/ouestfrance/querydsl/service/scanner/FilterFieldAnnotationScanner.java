package fr.ouestfrance.querydsl.service.scanner;

import fr.ouestfrance.querydsl.FilterField;
import fr.ouestfrance.querydsl.FilterFields;
import fr.ouestfrance.querydsl.model.FilterFieldInfoModel;
import fr.ouestfrance.querydsl.model.FilterFieldModel;
import fr.ouestfrance.querydsl.service.validators.FilterFieldConstraintException;
import fr.ouestfrance.querydsl.service.validators.FilterFieldValidatorService;
import fr.ouestfrance.querydsl.service.validators.FilterFieldViolation;
import fr.ouestfrance.querydsl.utils.ReflectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Class that allow to scan a clazz and return object representation of FilterFieldModel
 * This class also validate using {@link FilterFieldValidatorService} that the annotations operations are supported
 * by the {@link java.lang.reflect.Type} of the field
 */
public class FilterFieldAnnotationScanner {

    private final FilterFieldValidatorService validatorService = new FilterFieldValidatorService();


    /**
     * Check if field present annotations like FilterField or FilterFields
     *
     * @return <code>true</code> if one of the annotations is present, otherwise <code>false</code>
     */
    private static boolean hasFilterFieldAnnotation(Field field) {
        return field.isAnnotationPresent(FilterField.class) || field.isAnnotationPresent(FilterFields.class);
    }

    /**
     * Scan a specific clazz
     *
     * @param clazz class to scan
     * @return List of FilterFieldModel
     * @throws FilterFieldConstraintException raise an exception if field annotation is not supported by field type or if clazz don't provide getter function for the type
     */
    public List<FilterFieldModel> scan(Class<?> clazz) {
        List<FilterFieldModel> list = Arrays.stream(clazz.getDeclaredFields())
                .filter(FilterFieldAnnotationScanner::hasFilterFieldAnnotation)
                .map(x -> {
                    List<FilterFieldInfoModel> filterFields = toFilterFieldInfoModel(x);
                    Method getterFunction = ReflectUtils.getGetter(clazz, x);
                    return new FilterFieldModel(x.getName(), filterFields, getterFunction, x.getType());
                }).toList();
        // Check for violations
        List<FilterFieldViolation> violations = validatorService.validate(list);
        if (!violations.isEmpty()) {
            throw new FilterFieldConstraintException(clazz, violations);
        }
        return list;
    }

    /**
     * Transform FilterField annotation to FilterFieldInfoModel
     *
     * @param field field containing the annotation
     * @return List of FilterFieldInfoModel
     */
    private List<FilterFieldInfoModel> toFilterFieldInfoModel(Field field) {
        return Arrays.stream(field.getAnnotationsByType(FilterField.class))
                .map(model -> new FilterFieldInfoModel(
                        firstNotEmpty(model.key(), field.getName()),
                        model.operation(), model.orNull())
                ).toList();
    }

    /**
     * Return the fieldName by getting the first item not empty
     *
     * @param key  declared key on the annotation
     * @param name field name
     * @return return fieldName if key is empty, otherwise the key
     */
    private String firstNotEmpty(String key, String name) {
        return key.isEmpty() ? name : key;
    }
}
