package ru.rss.aggregator.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.rss.aggregator.model.Subscription;

import java.util.List;
import java.util.Set;

@Repository
@Transactional
public interface SubscriptionRepository extends CommonRepository<Subscription, Long> {

    Subscription findByRssUrl(String rssUrl);

    List<Subscription> rssUrl(Set<String> rssUrls);
}
