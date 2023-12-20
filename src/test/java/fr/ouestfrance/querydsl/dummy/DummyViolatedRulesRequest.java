package fr.ouestfrance.querydsl.dummy;

import fr.ouestfrance.querydsl.FilterField;
import fr.ouestfrance.querydsl.FilterOperation.IN;
import fr.ouestfrance.querydsl.FilterOperation.LTE;
import fr.ouestfrance.querydsl.FilterOperation.LIKE;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DummyViolatedRulesRequest {

    @FilterField(operation = IN.class)
    private String code;

    @FilterField(operation = LTE.class, key = "edition")
    private List<String> editions;

    @FilterField(operation = LIKE.class)
    private int quantity;
}
