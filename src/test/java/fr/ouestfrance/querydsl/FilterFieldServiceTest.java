package fr.ouestfrance.querydsl;

import fr.ouestfrance.querydsl.dummy.DummyRequest;
import fr.ouestfrance.querydsl.dummy.DummyRequestOrGroupMultipleField;
import fr.ouestfrance.querydsl.dummy.DummyRequestOrSingleField;
import fr.ouestfrance.querydsl.dummy.DummyViolatedRulesRequest;
import fr.ouestfrance.querydsl.model.SimpleFilter;
import fr.ouestfrance.querydsl.model.Filter;
import fr.ouestfrance.querydsl.service.FilterFieldAnnotationProcessorService;
import fr.ouestfrance.querydsl.service.validators.FilterFieldConstraintException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class FilterFieldServiceTest {

    private final FilterFieldAnnotationProcessorService service = new FilterFieldAnnotationProcessorService();

    @Test
    void shouldLoad() {
        List<Filter> model = service.process(DummyRequest.class);
        System.out.println(model.stream().map(Filter::toString).collect(Collectors.joining("\n")));
        assertNotNull(model);
        assertEquals(6, model.size());
        AtomicBoolean shouldFindOrNull = new AtomicBoolean(false);
        model.forEach(x -> {
            assertNotNull(x);
            assertTrue(x instanceof SimpleFilter);
            SimpleFilter filter = (SimpleFilter)x;
            assertNotNull(filter.field());
            shouldFindOrNull.set(shouldFindOrNull.get() | filter.orNull());
        });
        assertTrue(shouldFindOrNull.get());
    }

    @Test
    void shouldLoadComplexe() {
        List<Filter> model = service.process(DummyRequestOrSingleField.class);
        System.out.println(model.stream().map(Filter::toString).collect(Collectors.joining("\n\n")));
        assertNotNull(model);
    }

    @Test
    void shouldLoadComplexeMultiple() {
        List<Filter> model = service.process(DummyRequestOrGroupMultipleField.class);
        System.out.println(model.stream().map(Filter::toString).collect(Collectors.joining("\n\n")));
        assertNotNull(model);
    }

    @Test
    void shouldRaiseException() {
        FilterFieldConstraintException exception = assertThrows(FilterFieldConstraintException.class, () -> service.process(DummyViolatedRulesRequest.class));
        assertEquals(DummyViolatedRulesRequest.class, exception.getClazz());
        assertEquals(3, exception.getViolations().size());
    }
}
