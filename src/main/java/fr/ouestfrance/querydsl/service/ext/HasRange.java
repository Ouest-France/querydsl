package fr.ouestfrance.querydsl.service.ext;

/**
 * Interface for range values
 *
 * @param <T> the type of the range (like localDate, String, integers, ...)
 */
public interface HasRange<T> {

    /**
     * Get the lower bound of the range
     *
     * @return the lower bound or null if unbounded
     */
    T getLower();

    /**
     * Get the upper bound of the range
     *
     * @return the upper bound or null if unbounded
     */
    T getUpper();

    /**
     * Check if the lower bound is inclusive
     *
     * @return true if the lower bound is inclusive
     */
    boolean isLowerInclusive();

    /**
     * Check if the upper bound is inclusive
     *
     * @return true if the upper bound is inclusive
     */
    boolean isUpperInclusive();
}
