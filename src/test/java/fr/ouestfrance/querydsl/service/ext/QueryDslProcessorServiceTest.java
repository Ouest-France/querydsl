package fr.ouestfrance.querydsl.service.ext;

import fr.ouestfrance.querydsl.FilterOperation;
import fr.ouestfrance.querydsl.dummy.DummyRequest;
import fr.ouestfrance.querydsl.model.FilterFieldInfoModel;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;

class QueryDslProcessorServiceTest {

    QueryDslProcessorService<String> translatorService = operation -> new Mapper<>() {
        @Override
        public String map(FilterFieldInfoModel filterField, Object data) {
            return filterField.getKey() + "=" + data;
        }

        @Override
        public FilterOperation operation() {
            return null;
        }
    };

    @Test
    void shouldTranslate() {
        DummyRequest object = new DummyRequest();
        object.setCode("25");
        object.setValidityDate(LocalDate.of(2023, 12, 12));
        object.setValid(true);
        List<String> translate = translatorService.process(object);
        System.out.println(translate);
        assertEquals(4, translate.size());
        assertLinesMatch(List.of("productCode=25", "startDate=2023-12-12", "endDate=2023-12-12", "valid=true"), translate);
    }
}
