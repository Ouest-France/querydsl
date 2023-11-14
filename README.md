<div style="text-align:center;padding-top: 15px">
    <img alt="logo-ouest-france" src="https://sipaui.sipaof.fr/downloads/logotheque/ouest-france-couleur.svg" height="100"/>
    <h1 style="margin: 0;padding: 0">QueryDSL</h1>
</div>

---
<div style="text-align: center">

[![Build Status](https://github.com/Ouest-France/querydsl/actions/workflows/ci.yaml/badge.svg?branch=main)](https://github.com/PostgREST/postgrest/actions?query=branch%3Amain)
[![Coverage Status](https://img.shields.io/codecov/c/github/postgrest/postgrpostgrest/main)](https://app.codecov.io/gh/PostgREST/postgrest)
[![Hackage docs](https://img.shields.io/hackage/v/postgrest.svg?label=hackage)](http://hackage.haskell.org/package/postgrest)

</div>
QueryDSL is a unpostgrified annotation based DSL for querying multiple datasource

## Getting Started

--- 

QueryDSL is a **Domain Specific Language** based on annotation processing to transform java classes to dedicated query for a specific datasource.
You can write your own connector implementation by adding this library to your code

### Maven integration

Add the following dependency to your Maven project:

```xml
<dependency>
  <groupId>fr.ouestfrance.querydsl</groupId>
  <artifactId>querydsl</artifactId>
  <version>${querydsl.version}</version>
</dependency>
```

### Gradle Intégration

Add the following dependency to your gradle project:

```groovy
implementation 'fr.ouestfrance.querydsl:querydsl:${querydsl.version}'
```

## How does it work

---

QueryDSL transforms annotations from your search object to filterModel that you can map to a specific connector.

**Search object using annotations**

```java
import fr.ouestfrance.querydsl.FilterField;
import fr.ouestfrance.querydsl.FilterOperation;

import java.time.LocalDate;

class UserSearch {
    @FilterField(operation = FilterOperation.LIKE)
    String name; // John Doe
    @FilterField
    String service; // CIA
    @FilterField(operation = FilterOperation.LTE, key = "endDate")
    @FilterField(operation = FilterOperation.GTE, key = "startDate", orNull = true)        
    LocalDate searchDate; // 2023-12-12
}
```

**Concrete query using PostgREST**

```properties
GET /users?name=like.Jean%20Doe
              &service=eq.CIA
              &startDate=lte.2023-12-12
              &or=(endDate.eq.null, endDate.gte.2023-12-12)
```

**Concrete query using Raw SQL**
```sql
SELECT * FROM users
WHERE name LIKE "%John Doe%"
AND service = "CIA"
AND startDate < "2023-12-12"
AND (endDate is null OR endDate > "2023-12-12")
```

### Create your mappers

---

To achieve the transformations between annotation based and concrete source, you have to write Mappers

```java
package postgrest.mappers;

import fr.ouestfrance.querydsl.model.FilterFieldInfoModel;
import fr.ouestfrance.querydsl.service.ext.Mapper;
import fr.ouestfrance.querydsl.FilterOperation;


/**
 * Abstract mapper to simplify sub mapping
 */
public class EqualsMapper implements Mapper<String> {

    @Override
    public String map(FilterFieldInfoModel filterField, Object data) {
        return filterField.getKey() + "=eq." + data;
        // Will transform model to queryString "$key=eq.value" => example : service=eq.CIA
    }

    public FilterOperation getOperation(){ return FilterOperation.EQ; }
}
```

You can write mapper for each `FilterOperation` and have a specific transformation

```java
package postgrest.mappers;

import fr.ouestfrance.querydsl.FilterOperation;
import fr.ouestfrance.querydsl.service.ext.Mapper;
import postgrest.model.exceptions.PostgrestRequestException;

import java.util.Collection;
import java.util.stream.Collectors;


/**
 * Concrete mapping for in list
 */
public class InMapper extends Mapper<String> {

    @Override
    public String getFilter(FilterFieldInfoModel filterField, Object data) {
        if (value instanceof Collection<?> col) {
            return filterField.getKey() + "=in." + collectionToString(col);
        }
        throw new PostgrestRequestException("Filter " + operation() + " should be on collection type but was " + value.getClass().getSimpleName());
    }

    private String collectionToString(Collection<?> col) {
        return "(" + col.stream().map(Object::toString).collect(Collectors.joining(",")) + ")";
    }


    @Override
    public FilterOperation operation() {
        return FilterOperation.IN;
    }
}

```

### Create a processor Service

---

You have to bind your mappers to a processorService 

```java 
public class PostgrestQueryProcessorService implements QueryDslProcessorService<String> {

    private static final List<Mapper<Filter>> MAPPERS = List.of(new EqualsMapper(),
            new GreaterThanEqualsMapper(), new GreaterThanMapper(),
            new InMapper(), new LessThanEqualsMapper(), new LessThanMapper(),
            new LikeMapper(), new NotEqualsMapper(), new NotInMapper());

    public Mapper<Filter> getMapper(FilterOperation operation) {
        return MAPPERS.stream()
                .filter(x -> x.operation().equals(operation))
                .findFirst().orElseThrow();
    }
}
```

then you can add it to an abstract repository usage and reduce the amount of condition on your code

```java
import fr.ouestfrance.shared.querydsl.service.ext.QueryDslProcessorService;

import java.util.List;
import java.util.stream.Collectors;

public abstract class PostgrestRepository<T> {

    @Autowired
    private QueryDslProcessorService<String> processorService;
    @Autowired
    private WebClient webClient;

    public List<T> search(Object criteria) {
        List<String> queryStrings = processorService.process(criteria);
        return webClient.get(resourceUrl() + "?" + String.join("&", queryStrings));
    }
    
    abstract String resourceUrl();
}
```

## Need Help ?

---
If you need help with the library please start a new thread QA / Issue on github

## Contributing

---
If you want to request a feature or report a bug, please create a GitHub Issue

If you want to make a contribution to the project, please create a PR

## License

---
The QueryDSL is licensed under [MIT License](https://opensource.org/license/mit/)