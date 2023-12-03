package fr.ouestfrance.querydsl.service.scanner;

import fr.ouestfrance.querydsl.dummy.DummyRequest;
import fr.ouestfrance.querydsl.dummy.DummyRequestOrGroupMultipleField;
import fr.ouestfrance.querydsl.model.Filter;
import fr.ouestfrance.querydsl.model.GroupFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FilterFieldAnnotationScannerTest {

    private final FilterFieldAnnotationScanner scanner = new FilterFieldAnnotationScanner();
    @Test
    void shouldScanModel(){
        List<Filter> scan = scanner.scan(DummyRequest.class);
        assertNotNull(scan);
        assertEquals(6, scan.size());
    }

    @Test
    void shouldScanGroupMultiFieldModel(){
        List<Filter> scan = scanner.scan(DummyRequestOrGroupMultipleField.class);
        assertNotNull(scan);
        assertEquals(1, scan.size());
        assertEquals(1, scan.stream().filter(GroupFilter.class::isInstance).count());
    }
}
