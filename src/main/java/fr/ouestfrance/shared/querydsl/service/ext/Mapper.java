package fr.ouestfrance.shared.querydsl.service.ext;

import fr.ouestfrance.shared.querydsl.FilterOperation;
import fr.ouestfrance.shared.querydsl.model.FilterFieldInfoModel;

/**
 * Interface that allow to transform operation to concrete filter objet depending on the connector source
 * It allows to implements multiple kind of mappers like Hibernate, Rest, SOAP, GraphQL, ...)
 * Example :
 * <pre>
 * class RestEqualsMapper implements Mapper<String>{
 *     String map(FilterFieldInfoModel filterField, Object data){
 *         return filterField+"=eq."+data:
 *     }
 * }
 * </pre>
 * <pre>
 * class HibernateEqualsMapper implements Mapper<Specification>{
 *     protected HibernateEqualsMapper(CriteriaSpecificationBuilder cb, Context context)
 *     Specification<?> map(FilterFieldInfoModel filterField, Object data){
 *         return cb.equals(context.get(filterField), data):
 *     }
 * }
 * </pre>
 */
public interface Mapper<T> {

    /**
     * transform filterField info model to T implementation
     *
     * @param filterField model of filterfield
     * @param data        value data to apply to filter
     * @return <T> criteria representation
     */
    T map(FilterFieldInfoModel filterField, Object data);

    /**
     * type of the operation handled by the mapper
     *
     * @return operation type
     */
    FilterOperation operation();
}
