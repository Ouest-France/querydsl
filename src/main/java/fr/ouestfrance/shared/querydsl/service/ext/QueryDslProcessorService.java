package fr.ouestfrance.shared.querydsl.service.ext;

import fr.ouestfrance.shared.querydsl.FilterOperation;
import fr.ouestfrance.shared.querydsl.model.FilterFieldModel;
import fr.ouestfrance.shared.querydsl.service.FilterFieldAnnotationProcessorService;
import fr.ouestfrance.shared.querydsl.utils.ReflectUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * QueryDslProcessorService is an SPI extension that allow to write a custom translator to this DSL
 *
 * @param <T> Type of the returned object
 * Example: YOu can define a queryStringTranslatorService that transform filterField annotations to rest call
 * <pre>
 * class QueryStringTranslatorService extends TranslatorService<String>{
 *      List<Mapper<String>> mappers = List.of(InMapper, EqualsMapper, ...);
 *
 *      protected Mapper<String> getMapper(FilterOperation operations){
 *          mappers.stream().filter(x->x.getOperation().equals(operation)).findFirst()
 *           .orElse(new DefaultMapper());
 *      }
 * }
 *
 * </pre>
 * With mappers implementations like this
 * <pre>
 * class EqualsMapper implements Mapper<String>{
 *
 *      String map(FilterFieldInfoModel model, Object data){
 *          return model.getKey()+".equals("+data+")"
 *           // "uuid.equals(25)"
 *      }
 * }
 * </pre>
 */
public interface QueryDslProcessorService<T> {

    FilterFieldAnnotationProcessorService filterFieldService = new FilterFieldAnnotationProcessorService();

    /**
     * Transform un objet contenant des annotations filterFields en liste de critère
     *
     * @param object objet a consulter
     * @return liste de critere
     */
    default List<T> process(Object object) {
        List<T> list = new ArrayList<>();
        if (object != null) {
            List<FilterFieldModel> models = filterFieldService.process(object.getClass());
            models.forEach(x -> {
                Method getter = x.getGetter();
                Object data = ReflectUtils.safeGet(object, getter);
                if (data != null) {
                    x.getFilterFields().forEach(filter -> {
                        FilterOperation operation = filter.getOperation();
                        Mapper<T> mapper = getMapper(operation);
                        list.add(mapper.map(filter, data));
                    });
                }
            });
        }
        return list;
    }

    /**
     * Return concrete mapper for a specific operation
     *
     * @param operation type of operation
     * @return mapper for this operation
     */
    Mapper<T> getMapper(FilterOperation operation);
}
