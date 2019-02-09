package ru.rss.aggregator.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.rss.aggregator.model.StandartEntity;

@Repository
public interface StandartRepository extends PagingAndSortingRepository<StandartEntity, Long>, QuerydslPredicateExecutor<StandartEntity> {
}
