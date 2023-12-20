package fr.ouestfrance.querydsl.service.ext;

import fr.ouestfrance.querydsl.FilterOperation;
import fr.ouestfrance.querydsl.model.Filter;
import fr.ouestfrance.querydsl.model.GroupFilter;
import fr.ouestfrance.querydsl.model.SimpleFilter;
import fr.ouestfrance.querydsl.service.FilterFieldAnnotationProcessorService;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * QueryDslProcessorService is an SPI extension that allow to write a custom translator to this DSL
 *
 * @param <T> Type of returned object
 *            Example: YOu can define a queryStringTranslatorService that transform filterField annotations to rest call
 *            <pre>{@code
 *                                                                              class QueryStringTranslatorService implements QueryDslProcessorService<String>{
 *                                                                                   List<Mapper<String>> mappers = List.of(InMapper, EqualsMapper, ...);
 *
 *                                                                                   public Mapper<String> getMapper(FilterOperation operations){
 *                                                                                       mappers.stream().filter(x->x.getOperation().equals(operation)).findFirst()
 *                                                                                        .orElse(new DefaultMapper());
 *                                                                                   }
 *
 *                                                                                   public group(List<String> filters, GroupFilter.Operand operand){
 *                                                                                       return String.join(" "+operand+" ", filters); // Will result item1 AND item2 AND ...
 *                                                                                   }
 *                                                                              }
 *                                                                              }</pre>
 *            With mappers implementations like this
 *            <pre>{@code
 *                                                                              class EqualsMapper implements Mapper<String>{
 *
 *                                                                                   String map(FilterFieldInfoModel model, Object data){
 *                                                                                       return model.getKey()+".equals("+data+")"
 *                                                                                        // "uuid.equals(25)"
 *                                                                                   }
 *                                                                              }
 *                                                                              }</pre>
 */
public interface QueryDslProcessorService<T> {

    /**
     * Annotation processor service
     */
    FilterFieldAnnotationProcessorService filterFieldService = new FilterFieldAnnotationProcessorService();

    /**
     * Transform an annotated filterFields object to list of T criteria
     *
     * @param object objet a consulter
     * @return list of criteria result
     */
    default List<T> process(Object object) {
        List<T> list = new ArrayList<>();
        if (object != null) {
            List<Filter> models = filterFieldService.process(object.getClass());
            models.forEach(x -> {
                // If group
                if (x instanceof GroupFilter group) {
                    handleGroup(group, object).ifPresent(list::add);
                }
                // If filter
                if (x instanceof SimpleFilter filter) {
                    handleFilter(filter, object).ifPresent(list::add);
                }
            });
        }
        return list;
    }

    /**
     * Handle a groupFilter
     *
     * @param filterGroup filterGroup to handle
     * @param object      criteria object
     * @return concrete filter with values if the object has data
     */
    private Optional<T> handleGroup(GroupFilter filterGroup, Object object) {
        List<T> groupedItem = new ArrayList<>();
        filterGroup.filters().forEach(x -> {
            if (x instanceof SimpleFilter filter) {
                handleFilter(filter, object).ifPresent(groupedItem::add);
            } else if (x instanceof GroupFilter group) {
                handleGroup(group, object).ifPresent(groupedItem::add);
            }
        });
        return switch (groupedItem.size()) {
            // If no data found condition is not necessary
            case 0 -> Optional.empty();
            // If only on condition (logical expression is not necessary)
            case 1 -> groupedItem.stream().findFirst();
            // If more than one item then apply the group
            default -> Optional.ofNullable(group(groupedItem, filterGroup.operand()));
        };
    }

    /**
     * Handle a SimpleFilter
     *
     * @param filter filter to handle
     * @param object criteria object
     * @return concrete filter with values if the object has data
     */
    private Optional<T> handleFilter(SimpleFilter filter, Object object) {
        return ReflectUtils.getObject(filter.field(), object)
                .map(x -> getMapper(filter.operation()).map(filter, x));
    }


    /**
     * Return concrete mapper for a specific operation
     *
     * @param operation type of operation
     * @return mapper for this operation
     */
    Mapper<T> getMapper(Class<? extends FilterOperation> operation);

    /**
     * Allow to group multiple filters with logical operand
     *
     * @param filters filters to handle
     * @param operand operand (example AND, OR)
     * @return new filter representation combining the filters with the specified operator
     */
    T group(List<T> filters, GroupFilter.Operand operand);
}
