package fr.ouestfrance.querydsl.dummy;

import fr.ouestfrance.querydsl.FilterField;
import fr.ouestfrance.querydsl.FilterOperation;
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
