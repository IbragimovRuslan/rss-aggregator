package ru.rss.aggregator.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface CommonRepository<T, ID> extends PagingAndSortingRepository<T, ID>, QuerydslPredicateExecutor<T> {
}
