package fr.ouestfrance.querydsl;

import fr.ouestfrance.querydsl.service.validators.ValidatedBy;
import fr.ouestfrance.querydsl.service.validators.impl.CollectionValidator;
import fr.ouestfrance.querydsl.service.validators.impl.ComparableValidator;
import fr.ouestfrance.querydsl.service.validators.impl.StringValidator;

public interface FilterOperation {

    @ValidatedBy(ComparableValidator.class)
    class NEQ implements FilterOperation {
    }

    @ValidatedBy(ComparableValidator.class)
    class EQ implements FilterOperation {
    }

    @ValidatedBy(ComparableValidator.class)
    class GT implements FilterOperation {
    }

    @ValidatedBy(ComparableValidator.class)
    class GTE implements FilterOperation {
    }

    @ValidatedBy(ComparableValidator.class)
    class LT implements FilterOperation {
    }

    @ValidatedBy(ComparableValidator.class)
    class LTE implements FilterOperation {
    }

    @ValidatedBy(StringValidator.class)
    class LIKE implements FilterOperation {
    }

    @ValidatedBy(CollectionValidator.class)
    class IN implements FilterOperation {
    }

    @ValidatedBy(CollectionValidator.class)
    class NOTIN implements FilterOperation {
    }
}
