package fr.ouestfrance.querydsl.service;

import fr.ouestfrance.querydsl.model.Filter;
import fr.ouestfrance.querydsl.service.scanner.FilterFieldAnnotationScanner;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Main service that allow to transform annotated class to model based representation
 * This service use {@link FilterFieldAnnotationScanner} to handle transformation and keep in cache representations
 */
public class FilterFieldAnnotationProcessorService {

    /**
     * Cache of models
     */
    private static final Map<Class<?>, List<Filter>> CACHE = new ConcurrentHashMap<>();
    /**
     * Annotation scanner
     */
    private final FilterFieldAnnotationScanner scanner = new FilterFieldAnnotationScanner();

    /**
     * Process a specific class and return a list of filterFieldModel
     *
     * @param clazz clazz to handle
     * @return List of filterFieldModel
     */
    public List<Filter> process(Class<?> clazz) {
        return CACHE.computeIfAbsent(clazz, x -> scanner.scan(clazz));
    }
}
