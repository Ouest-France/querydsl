package fr.ouestfrance.querydsl;

import fr.ouestfrance.querydsl.service.validators.ValidatedBy;
import fr.ouestfrance.querydsl.service.validators.impl.*;

/**
 * Operations allowed by querydsl
 */
public interface FilterOperation {

    /**
     * Not Equals Operation
     */
    @ValidatedBy(ComparableValidator.class)
    class NEQ implements FilterOperation {
    }

    /**
     * Equals Operation
     */
    @ValidatedBy(ComparableValidator.class)
    class EQ implements FilterOperation {
    }

    /**
     * Greater than Operation
     */
    @ValidatedBy(ComparableValidator.class)
    class GT implements FilterOperation {
    }

    /**
     * Greater than or equals Operation
     */
    @ValidatedBy(ComparableValidator.class)
    class GTE implements FilterOperation {
    }

    /**
     * Less than Operation
     */
    @ValidatedBy(ComparableValidator.class)
    class LT implements FilterOperation {
    }

    /**
     * Less than or equals Operation
     */
    @ValidatedBy(ComparableValidator.class)
    class LTE implements FilterOperation {
    }

    /**
     * Like Operation
     */
    @ValidatedBy(StringValidator.class)
    class LIKE implements FilterOperation {
    }

    /**
     * Not Like Operation
     */
    @ValidatedBy(CollectionValidator.class)
    class IN implements FilterOperation {
    }

    /**
     * Not In Operation
     */
    @ValidatedBy(CollectionValidator.class)
    class NOTIN implements FilterOperation {
    }

    /**
     * Is null Operation
     */
    @ValidatedBy(BooleanValidator.class)
    class ISNULL implements FilterOperation {
    }


    /**
     * Between Operation
     */
    @ValidatedBy(HasRangeValidator.class)
    class BETWEEN implements FilterOperation {
    }
}
