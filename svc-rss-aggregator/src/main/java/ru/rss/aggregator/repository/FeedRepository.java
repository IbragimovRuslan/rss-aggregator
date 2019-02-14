package ru.rss.aggregator.repository;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.rss.aggregator.model.Feed;

@Repository
@Transactional
public interface FeedRepository extends CommonRepository<Feed, Long> {
}
