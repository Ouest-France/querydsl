package fr.ouestfrance.querydsl.service.ext;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * Internal reflect class that allow to retrieve object
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class ReflectUtils {


    /**
     * Get safe value for a field
     *
     * @param field  to retrieve data
     * @param object source object
     * @return optional value
     */
    @SneakyThrows
    @SuppressWarnings("java:S3011")
    public static Optional<Object> getObject(Field field, Object object) {
        // allow to access the field if record or field from clazz
        field.setAccessible(true);
        return Optional.ofNullable(field.get(object));
    }
}
