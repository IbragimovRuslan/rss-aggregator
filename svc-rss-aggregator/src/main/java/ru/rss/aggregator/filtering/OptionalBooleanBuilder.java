package ru.rss.aggregator.filtering;


import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class OptionalBooleanBuilder {

    private final BooleanExpression predicate;

    OptionalBooleanBuilder(BooleanExpression predicate) {
        this.predicate = predicate;
    }

    public <T> OptionalBooleanBuilder notNullAnd(Function<T, BooleanExpression> expressionFunction, T value) {
        if (value != null) {
            return new OptionalBooleanBuilder(predicate.and(expressionFunction.apply(value)));
        }
        return this;
    }

    public OptionalBooleanBuilder notBlankAnd(Function<String, BooleanExpression> expressionFunction, String value) {
        if (StringUtils.isEmpty(value)) {
            return new OptionalBooleanBuilder(predicate.and(expressionFunction.apply(value)));
        }
        return this;
    }

    public <T> OptionalBooleanBuilder notEmptyAnd(Function<Collection<T>, BooleanExpression> expressionFunction, Collection<T> collection) {
        if (!CollectionUtils.isEmpty(collection)) {
            return new OptionalBooleanBuilder(predicate.and(expressionFunction.apply(collection)));
        }
        return this;
    }

    public OptionalBooleanBuilder notBlankAndGroupedOr(Map<Function<String, BooleanExpression>, String> groupedArguments) {
        // Отфильтруем аргументы - нам нужны только не пустые.
        Set<Map.Entry<Function<String, BooleanExpression>, String>> entries = groupedArguments.entrySet();
        entries.removeIf(entry -> StringUtils.isEmpty(entry.getValue()));

        // Соберём выражение.
        BooleanExpression result = null;
        for (Map.Entry<Function<String, BooleanExpression>, String> entry : entries) {
            if (result != null) {
                // Мы на промежуточном этапе, применим предыдущие с текущим через операцию OR
                result = result.or(entry.getKey().apply(entry.getValue()));

            } else {
                // Значит, мы тут впервые, инициализируем будущий результат.
                result = entry.getKey().apply(entry.getValue());
            }
        }
        // Вернём результат оперции AND (A слева) и результата композиции справа: A && (B || C || D || ...).
        return new OptionalBooleanBuilder(predicate.and(result));
    }

    public BooleanExpression build() {
        return predicate;
    }
}
