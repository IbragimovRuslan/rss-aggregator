package ru.rss.aggregator.filtering;

import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class StandardFilter {
    private Long id;
    private String name;
    private String description;

    public abstract Predicate toPredicate();
}
