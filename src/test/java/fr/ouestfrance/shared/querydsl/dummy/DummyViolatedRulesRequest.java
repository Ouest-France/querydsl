package fr.ouestfrance.shared.querydsl.dummy;

import fr.ouestfrance.shared.querydsl.FilterField;
import fr.ouestfrance.shared.querydsl.FilterOperation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DummyViolatedRulesRequest {

    @FilterField(operation = FilterOperation.IN)
    private String code;

    @FilterField(operation = FilterOperation.LTE, key = "edition")
    private List<String> editions;

    @FilterField(operation = FilterOperation.LIKE)
    private int quantity;
}
