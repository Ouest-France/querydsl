package fr.ouestfrance.querydsl.dummy;

import fr.ouestfrance.querydsl.FilterField;
import fr.ouestfrance.querydsl.FilterOperation;
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

    @FilterField(operation = FilterOperation.IN.class, key = "edition")
    private List<String> editions;

    @FilterField(operation = FilterOperation.GTE.class, key = "startDate")
    @FilterField(operation = FilterOperation.LTE.class, key = "endDate", orNull = true)
    private LocalDate validityDate;

    @FilterField
    private boolean valid;

    private String noFilterField;
}

