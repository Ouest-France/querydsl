package fr.ouestfrance.shared.querydsl.utils;

import fr.ouestfrance.shared.querydsl.service.validators.FilterFieldConstraintException;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReflectUtilsTest {

    @Test
    void shouldRaiseExceptionOnGetter() throws NoSuchFieldException {
        Field value = DummyTest.class.getDeclaredField("value");
        assertThrows(FilterFieldConstraintException.class, () -> ReflectUtils.getGetter(DummyTest.class, value));
    }

    @Test
    void shouldRaiseExceptionOnGetterInaccessible() throws NoSuchFieldException {
        Field value = DummyTest.class.getDeclaredField("data");
        Method getter = ReflectUtils.getGetter(DummyTest.class, value);
        Object result = ReflectUtils.safeGet(new DummyTest(1, "Example"), getter);
        assertNull(result);
    }

    @AllArgsConstructor
    static class DummyTest {
        private int value;
        private String data;

        private String getData() {
            return data;
        }
    }
}
