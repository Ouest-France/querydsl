package fr.ouestfrance.shared.querydsl.service.validators.impl;

import fr.ouestfrance.shared.querydsl.service.validators.FilterFieldValidator;
import fr.ouestfrance.shared.querydsl.service.validators.impl.LikeValidator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LikeValidatorTest {

    private final FilterFieldValidator validator = new LikeValidator();
    @Test
    void shouldValidate(){
        assertTrue(validator.validate(String.class));
    }

    @Test
    void shouldUnValidate(){
        assertFalse(validator.validate(Double.class));
        assertFalse(validator.validate(LocalDate.class));
        assertFalse(validator.validate(LocalDateTime.class));
        assertFalse(validator.validate(Date.class));
        assertFalse(validator.validate(UUID.class));
        assertFalse(validator.validate(Integer.class));
        assertFalse(validator.validate(BigDecimal.class));
        assertFalse(validator.validate(Set.class));
        assertFalse(validator.validate(ArrayList.class));
        assertFalse(validator.validate(LinkedList.class));
        assertFalse(validator.validate(HashSet.class));
    }
}
