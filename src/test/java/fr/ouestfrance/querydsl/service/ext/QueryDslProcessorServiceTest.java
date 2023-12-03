package fr.ouestfrance.querydsl.service.ext;

import fr.ouestfrance.querydsl.FilterOperation;
import fr.ouestfrance.querydsl.dummy.DummyRequest;
import fr.ouestfrance.querydsl.dummy.DummyRequestOrGroupMultipleField;
import fr.ouestfrance.querydsl.dummy.DummyRequestOrSingleField;
import fr.ouestfrance.querydsl.model.GroupFilter;
import fr.ouestfrance.querydsl.model.SimpleFilter;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;

class QueryDslProcessorServiceTest {

    QueryDslProcessorService<String> translatorService = new QueryDslProcessorService<>() {
        @Override
        public Mapper<String> getMapper(FilterOperation operation) {
            return new Mapper<>() {
                @Override
                public String map(SimpleFilter filterField, Object data) {
                    return filterField.key() + "=" + data;
                }

                @Override
                public FilterOperation operation() {
                    return null;
                }
            };
        }

        @Override
        public String group(List<String> filters, GroupFilter.Operand operand) {
            return "( " + String.join(" " + operand + " ", filters) + " )";
        }
    };

    @Test
    void shouldTranslate() {
        DummyRequest object = new DummyRequest();
        object.setCode("25");
        object.setValidityDate(LocalDate.of(2023, 12, 12));
        object.setValid(true);
        List<String> translate = translatorService.process(object);
        assertEquals("productCode=25 AND startDate=2023-12-12 AND endDate=2023-12-12 AND valid=true", String.join(" AND ", translate));
        assertEquals(4, translate.size());
        assertLinesMatch(List.of("productCode=25", "startDate=2023-12-12", "endDate=2023-12-12", "valid=true"), translate);
    }

    @Test
    void shouldTranslateOr() {
        DummyRequestOrGroupMultipleField object = new DummyRequestOrGroupMultipleField();
        object.setDefaultSize("25");
        object.setSize("12");
        List<String> translate = translatorService.process(object);
        assertEquals("( size=12 OR defaultSize=25 )", String.join(" AND ", translate));
    }

    @Test
    void shouldTranslateCompositeMixin() {
        DummyRequestOrSingleField object = new DummyRequestOrSingleField();
        object.setSize("12");
        List<String> translate = translatorService.process(object);
        assertEquals("( ( minSize=12 AND maxSize=12 ) OR size=12 )", String.join(" AND ", translate));
    }

    @Test
    void shouldTranslateWithNullValue() {
        List<String> translate = translatorService.process(null);
        assertEquals(0, translate.size());
    }

    @Test
    void shouldTranslateOrWithOneValue() {
        DummyRequestOrGroupMultipleField object = new DummyRequestOrGroupMultipleField();
        object.setDefaultSize("12");
        List<String> translate = translatorService.process(object);
        String result = String.join(" AND ", translate);
        assertEquals("defaultSize=12", result);
    }

    @Test
    void shouldTranslateOrWithZeroValues() {
        DummyRequestOrGroupMultipleField object = new DummyRequestOrGroupMultipleField();
        List<String> translate = translatorService.process(object);
        String result = String.join(" AND ", translate);
        assertEquals("", result);
    }
}
