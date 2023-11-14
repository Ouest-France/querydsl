package fr.ouestfrance.querydsl;

/**
 * Type of operations
 */
public enum FilterOperation {
    /**
     * Should be equals
     */
    EQ,
    /**
     * Should contains data
     */
    LIKE,
    /**
     * Should be greater than
     */
    GT,
    /**
     * Should be greater than or equals to
     */
    GTE,
    /**
     * Should be less than
     */
    LT,
    /**
     * Should be less than or equals to
     */
    LTE,
    /**
     * Should be not equals to
     */
    NEQ,
    /**
     * Should be in a specific list
     */
    IN,
    /**
     * Should not be in a specific list
     */
    NOT_IN
}
