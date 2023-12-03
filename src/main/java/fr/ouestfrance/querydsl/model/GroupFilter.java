package fr.ouestfrance.querydsl.model;

import java.util.List;

/**
 * Group filter allow to create a filter with logical operand and sub filters
 * @param name of the group
 * @param filters list of filters in the group
 * @param operand logical operator to apply
 */
public record GroupFilter(String name, List<Filter> filters, Operand operand) implements Filter {

    /**
     * Operand types
     */
    public enum Operand {
        /**
         * OR Operand
         */
        OR,
        /**
         * AND Operand
         */
        AND
    }
}
