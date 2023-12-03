package fr.ouestfrance.querydsl.dummy;

import fr.ouestfrance.querydsl.FilterField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DummyRequestOrGroupMultipleField {

    // size = $size OR defaultSize = $defaultSize

    @FilterField(key = "size", groupName = "group1")
    private String size;

    @FilterField(key = "defaultSize", groupName ="group1")
    private String defaultSize;

}

