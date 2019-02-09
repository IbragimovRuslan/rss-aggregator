package ru.rss.aggregator.repository;


import org.springframework.stereotype.Repository;
import ru.rss.aggregator.model.Feed;

@Repository
public interface FeedRepository extends CommonRepository<Feed, Long> {
}
