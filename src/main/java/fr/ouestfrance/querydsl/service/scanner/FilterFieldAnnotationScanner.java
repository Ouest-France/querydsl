package fr.ouestfrance.querydsl.service.scanner;

import fr.ouestfrance.querydsl.FilterField;
import fr.ouestfrance.querydsl.FilterFields;
import fr.ouestfrance.querydsl.model.Filter;
import fr.ouestfrance.querydsl.model.GroupFilter;
import fr.ouestfrance.querydsl.model.SimpleFilter;
import fr.ouestfrance.querydsl.service.validators.FilterFieldConstraintException;
import fr.ouestfrance.querydsl.service.validators.FilterFieldValidatorService;
import fr.ouestfrance.querydsl.service.validators.FilterFieldViolation;

import java.lang.reflect.Field;
import java.util.*;

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
    private boolean hasFilterFieldAnnotation(Field field) {
        return field.isAnnotationPresent(FilterField.class) || field.isAnnotationPresent(FilterFields.class);
    }

    /**
     * Scan a specific clazz
     *
     * @param clazz class to scan
     * @return List of FilterFieldModel
     * @throws FilterFieldConstraintException raise an exception if field annotation is not supported by field type or if clazz don't provide getter function for the type
     */
    public List<Filter> scan(Class<?> clazz) {
        GroupFilter rootGroup = new GroupFilter("$root#", new ArrayList<>(), GroupFilter.Operand.AND);

        List<FilterFieldViolation> violations = new ArrayList<>();

        Arrays.stream(clazz.getDeclaredFields())
                .filter(this::hasFilterFieldAnnotation)
                .forEach(
                field -> {
                     FilterFields filterFields = field.getAnnotation(FilterFields.class);
                    List<FilterField> groupFilters = new ArrayList<>();
                    if (filterFields!= null && !filterFields.groupName().isEmpty()) {
                        groupFilters.addAll(Arrays.stream(filterFields.value()).toList());
                        GroupFilter filterAndGroup = new GroupFilter(UUID.randomUUID().toString(), new ArrayList<>(), GroupFilter.Operand.AND);
                        Arrays.stream(filterFields.value())
                                .forEach(filterField -> {
                                    SimpleFilter filter = new SimpleFilter(firstNotEmpty(filterField.key(), field.getName()), filterField.operation(), filterField.orNull(), field);
                                    validatorService.validate(filter).ifPresent(violations::add);
                                    filterAndGroup.filters().add(filter);
                                });
                        appendToGroup(rootGroup, filterFields.groupName(), filterAndGroup);
                    }

                    // On filterField
                    Arrays.stream(field.getAnnotationsByType(FilterField.class))
                            .filter(x-> !groupFilters.contains(x))
                            .forEach(
                            filterField -> {
                                SimpleFilter filter = new SimpleFilter(firstNotEmpty(filterField.key(), field.getName()), filterField.operation(), filterField.orNull(), field);
                                validatorService.validate(filter).ifPresent(violations::add);
                                appendToGroup(rootGroup, filterField.groupName(), filter);
                            }
                    );
                }
        );

        // Check for violations
        if (!violations.isEmpty()) {
            throw new FilterFieldConstraintException(clazz, violations);
        }
        return rootGroup.filters();
    }

    /**
     * Append a filter to a groupName, if no group found, it will compute a new OR Group
     * @param rootGroup root group
     * @param groupName name of the searched group
     * @param filter filter to append
     */
    private void appendToGroup(GroupFilter rootGroup, String groupName, Filter filter) {
        if (groupName.isEmpty()) {
            rootGroup.filters().add(filter);
        } else {
            // tree visit to find group or create it
            Optional<GroupFilter> group = getGroup(rootGroup, groupName);
            group.ifPresentOrElse(x -> x.filters().add(filter), () -> {
                GroupFilter filterGroup = new GroupFilter(groupName, new ArrayList<>(), GroupFilter.Operand.OR);
                rootGroup.filters().add(filterGroup);
                filterGroup.filters().add(filter);
            });
        }
    }

    /**
     * Retrieve a rootGroup by its name
     * @param rootGroup rootGroup
     * @param groupName name of the searched rootGroup
     * @return optional rootGroup
     */
    private Optional<GroupFilter> getGroup(GroupFilter rootGroup, String groupName) {
        return rootGroup.filters().stream()
                .filter(GroupFilter.class::isInstance)
                .map(GroupFilter.class::cast)
                .filter(filterGroup -> groupName.equals(filterGroup.name()))
                .findFirst();
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
