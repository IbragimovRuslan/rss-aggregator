package ru.rss.aggregator.repository;


import org.springframework.stereotype.Repository;
import ru.rss.aggregator.model.FeedItem;

import java.util.List;

@Repository
public interface FeedItemRepository extends CommonRepository<FeedItem, Long> {

    List<FeedItem> findBySubscriptionId(String subscriptionId);
}
