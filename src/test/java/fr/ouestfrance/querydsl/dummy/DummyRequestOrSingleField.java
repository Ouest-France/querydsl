package fr.ouestfrance.querydsl.dummy;

import fr.ouestfrance.querydsl.FilterField;
import fr.ouestfrance.querydsl.FilterFields;
import fr.ouestfrance.querydsl.FilterOperation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DummyRequestOrSingleField {

    // size = $size OR (minSize < size AND maxSize > size)

    @FilterField(key = "size", groupName = "group1")
    @FilterFields(groupName = "group1", value = {
            @FilterField(key = "minSize", operation = FilterOperation.GTE),
            @FilterField(key = "maxSize", operation = FilterOperation.LTE)
    })
    private String size;

}

