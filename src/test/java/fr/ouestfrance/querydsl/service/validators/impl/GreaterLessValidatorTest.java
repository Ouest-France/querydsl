package fr.ouestfrance.querydsl.service.validators.impl;

import fr.ouestfrance.querydsl.service.validators.FilterFieldValidator;
import fr.ouestfrance.querydsl.service.validators.impl.GreaterLessValidator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GreaterLessValidatorTest {

    private final FilterFieldValidator validator = new GreaterLessValidator();
    @Test
    void shouldValidate(){
        assertTrue(validator.validate(String.class));
        assertTrue(validator.validate(Double.class));
        assertTrue(validator.validate(LocalDate.class));
        assertTrue(validator.validate(LocalDateTime.class));
        assertTrue(validator.validate(Date.class));
        assertTrue(validator.validate(UUID.class));
        assertTrue(validator.validate(Integer.class));
        assertTrue(validator.validate(BigDecimal.class));
    }

    @Test
    void shouldUnValidate(){
        assertFalse(validator.validate(Set.class));
        assertFalse(validator.validate(ArrayList.class));
        assertFalse(validator.validate(LinkedList.class));
        assertFalse(validator.validate(HashSet.class));
    }
}
