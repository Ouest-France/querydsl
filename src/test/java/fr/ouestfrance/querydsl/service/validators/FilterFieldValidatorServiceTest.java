package fr.ouestfrance.querydsl.service.validators;

import fr.ouestfrance.querydsl.FilterOperation;
import fr.ouestfrance.querydsl.model.FieldMetadata;
import fr.ouestfrance.querydsl.model.SimpleFilter;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilterFieldValidatorServiceTest {

    private final FilterFieldValidatorService validator = new FilterFieldValidatorService();

    @Test
    void shouldSentViolations(){
        Optional<FilterFieldViolation> violations = validator.validate(
                new SimpleFilter("test", FilterOperation.EQ, false, new FieldMetadata("test", List.class, null)));

        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldValidate(){
        Optional<FilterFieldViolation> violations = validator.validate(
                new SimpleFilter("test", FilterOperation.EQ, false, new FieldMetadata("test", String.class, null)));

        assertTrue(violations.isEmpty());
    }

}
