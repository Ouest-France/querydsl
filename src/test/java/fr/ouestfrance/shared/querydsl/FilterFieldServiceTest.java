package fr.ouestfrance.shared.querydsl;

import fr.ouestfrance.shared.querydsl.dummy.DummyRequest;
import fr.ouestfrance.shared.querydsl.dummy.DummyViolatedRulesRequest;
import fr.ouestfrance.shared.querydsl.model.FilterFieldInfoModel;
import fr.ouestfrance.shared.querydsl.model.FilterFieldModel;
import fr.ouestfrance.shared.querydsl.service.FilterFieldAnnotationProcessorService;
import fr.ouestfrance.shared.querydsl.service.validators.FilterFieldConstraintException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class FilterFieldServiceTest {

    private final FilterFieldAnnotationProcessorService service = new FilterFieldAnnotationProcessorService();

    @Test
    void shouldLoad() {
        List<FilterFieldModel> model = service.process(DummyRequest.class);
        assertNotNull(model);
        assertEquals(5, model.size());
        AtomicBoolean shouldFindOrNull = new AtomicBoolean(false);
        model.forEach(x -> {
            assertNotNull(x.getName());
            assertNotNull(x.getGetter());
            assertNotNull(x.getType());
            assertNotNull(x.getFilterFields());
            shouldFindOrNull.set(shouldFindOrNull.get() | x.getFilterFields().stream()
                    .anyMatch(FilterFieldInfoModel::isOrNull));
        });

        assertTrue(shouldFindOrNull.get());
    }

    @Test
    void shouldRaiseException() {
        FilterFieldConstraintException exception = assertThrows(FilterFieldConstraintException.class, () -> service.process(DummyViolatedRulesRequest.class));
        assertEquals(DummyViolatedRulesRequest.class, exception.getClazz());
        assertEquals(3, exception.getViolations().size());
    }
}