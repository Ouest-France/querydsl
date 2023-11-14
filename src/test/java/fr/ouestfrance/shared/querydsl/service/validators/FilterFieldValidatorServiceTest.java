package fr.ouestfrance.shared.querydsl.service.validators;

import fr.ouestfrance.shared.querydsl.FilterOperation;
import fr.ouestfrance.shared.querydsl.model.FilterFieldInfoModel;
import fr.ouestfrance.shared.querydsl.model.FilterFieldModel;
import fr.ouestfrance.shared.querydsl.service.validators.FilterFieldValidatorService;
import fr.ouestfrance.shared.querydsl.service.validators.FilterFieldViolation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilterFieldValidatorServiceTest {

    private final FilterFieldValidatorService validator = new FilterFieldValidatorService();

    @Test
    void shouldSentViolations(){
        List<FilterFieldViolation> violations = validator.validate(List.of
                (new FilterFieldModel("test",
                List.of(new FilterFieldInfoModel("", FilterOperation.EQ, false))
                ,  null,List.class)));

        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldValidate(){
        List<FilterFieldViolation> violations = validator.validate(List.of
                (new FilterFieldModel("test",
                        List.of(new FilterFieldInfoModel("", FilterOperation.EQ, false))
                        , null, String.class)));

        assertTrue(violations.isEmpty());
    }

}
