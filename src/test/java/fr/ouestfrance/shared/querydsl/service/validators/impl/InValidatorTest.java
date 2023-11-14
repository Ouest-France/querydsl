package fr.ouestfrance.shared.querydsl.service.validators.impl;

import fr.ouestfrance.shared.querydsl.service.validators.FilterFieldValidator;
import fr.ouestfrance.shared.querydsl.service.validators.impl.InValidator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InValidatorTest {

    private final FilterFieldValidator validator = new InValidator();
    @Test
    void shouldValidate(){
        assertTrue(validator.validate(Set.class));
        assertTrue(validator.validate(ArrayList.class));
        assertTrue(validator.validate(LinkedList.class));
        assertTrue(validator.validate(HashSet.class));
    }

    @Test
    void shouldUnValidate(){
        assertFalse(validator.validate(String.class));
        assertFalse(validator.validate(Double.class));
        assertFalse(validator.validate(LocalDate.class));
        assertFalse(validator.validate(LocalDateTime.class));
        assertFalse(validator.validate(Date.class));
        assertFalse(validator.validate(UUID.class));
        assertFalse(validator.validate(Integer.class));
        assertFalse(validator.validate(BigDecimal.class));
    }
}
