package ru.rss.aggregator.repository;

import org.springframework.stereotype.Repository;
import ru.rss.aggregator.model.Subscription;

import java.util.List;
import java.util.Set;

@Repository
public interface SubscriptionRepository extends CommonRepository<Subscription, Long> {

    Subscription findByRssUrl(String rssUrl);

    List<Subscription> findByRssUrls(Set<String> rssUrls);
}
