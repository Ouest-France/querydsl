package fr.ouestfrance.shared.querydsl.dummy;

import fr.ouestfrance.shared.querydsl.FilterField;
import fr.ouestfrance.shared.querydsl.FilterOperation;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class DummyRequest {

    @FilterField
    private String uuid;

    @FilterField(key = "productCode")
    private String code;

    @FilterField(operation = FilterOperation.IN, key = "edition")
    private List<String> editions;

    @FilterField(operation = FilterOperation.GTE, key = "startDate")
    @FilterField(operation = FilterOperation.LTE, key = "endDate", orNull = true)
    private LocalDate validityDate;

    @FilterField
    private boolean valid;
}
