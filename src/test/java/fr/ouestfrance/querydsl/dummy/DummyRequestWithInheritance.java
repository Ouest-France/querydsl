package fr.ouestfrance.querydsl.dummy;

import fr.ouestfrance.querydsl.FilterField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DummyRequestWithInheritance extends DummyRequest {

    @FilterField(key = "anotherProductCode")
    private String anotherCode;
}

