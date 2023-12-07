package fr.ouestfrance.querydsl.service.validators;

import fr.ouestfrance.querydsl.FilterOperation;
import fr.ouestfrance.querydsl.model.SimpleFilter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilterFieldValidatorServiceTest {

    private final FilterFieldValidatorService validator = new FilterFieldValidatorService();

    static class SampleClass{
        List<String> lists;
        String value;
    }

    @SneakyThrows
    @Test
    void shouldSentViolations(){
        Optional<FilterFieldViolation> violations = validator.validate(
                new SimpleFilter("test", FilterOperation.EQ, false, SampleClass.class.getDeclaredField("lists")));

        assertFalse(violations.isEmpty());
    }

    @SneakyThrows
    @Test
    void shouldValidate(){
        Optional<FilterFieldViolation> violations = validator.validate(
                new SimpleFilter("test", FilterOperation.EQ, false, SampleClass.class.getDeclaredField("value")));

        assertTrue(violations.isEmpty());
    }

}
