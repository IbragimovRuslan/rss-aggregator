package ru.rss.aggregator.service.message;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.util.StringUtils;
import ru.rss.aggregator.model.Feed;
import ru.rss.aggregator.model.FeedItem;
import ru.rss.aggregator.model.Subscription;
import ru.rss.aggregator.model.SyndFeedWrapper;
import ru.rss.aggregator.repository.FeedItemRepository;
import ru.rss.aggregator.repository.SubscriptionRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@MessageEndpoint
public class RssFeedMessageEndpoint {
    private static final Logger logger = LoggerFactory.getLogger(RssFeedMessageEndpoint.class);

    @Autowired
    private FeedItemRepository feedItemRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @ServiceActivator(inputChannel = "feedChannel", poller = @Poller(maxMessagesPerPoll = "1", fixedDelay = "10000"))
    public void handleFeeds(Message<List<SyndFeedWrapper>> message) throws IOException {
        List<SyndFeedWrapper> syndFeeds = message.getPayload();
        for (SyndFeedWrapper syndFeedWrapper : syndFeeds) {
            SyndFeed syndFeed = syndFeedWrapper.getSyndFeed();
            String feedUrl = syndFeed.getLink();
            if (feedUrl == null) {
                logger.warn("Feed Link undefined for FEED[title]: {}", syndFeed.getTitle());
                continue;
            }

            Subscription s = subscriptionRepository.findByRssUrl(syndFeedWrapper.getRssUrs());
            if (s == null) {
                logger.error("No subscription found for URL: " + syndFeedWrapper.getRssUrs());
                continue;
            }

            Feed feed = s.getFeed();
            if (feed != null && feed.getLastBuildDate().compareTo(LocalDateTime.ofInstant(syndFeed.getPublishedDate().toInstant(), ZoneId.systemDefault())) == 0) {
                logger.info("Found Feed {} - has actual date and will not be replaced with a new", feedUrl);
                continue;
            } else {
                feed = new Feed(feedUrl, syndFeed.getTitle(), LocalDateTime.ofInstant(syndFeed.getPublishedDate().toInstant(), ZoneId.systemDefault()));
                s.setFeed(feed);
                subscriptionRepository.save(s);
            }

            List<FeedItem> items = new ArrayList<>();

            logger.info("Rss feed Title: {}, URL: {}\n", syndFeed.getTitle(), syndFeed.getUri());

            for (SyndEntry syndEntry : syndFeed.getEntries()) {
                logger.info("\trss feed item infomation. author={}, title={}, link={}", syndEntry.getAuthor(),
                        syndEntry.getTitle(), syndEntry.getLink());

                String guid = syndEntry.getUri();
                if (StringUtils.isEmpty(guid)) {
                    guid = syndEntry.getLink();
                }
                if (StringUtils.isEmpty(guid)) {
                    continue;
                }

                FeedItem feedItem = new FeedItem(
                        guid,
                        syndEntry.getTitle(),
                        syndEntry.getDescription().getValue(),
                        syndEntry.getAuthor(),
                        LocalDateTime.ofInstant(syndFeed.getPublishedDate().toInstant(), ZoneId.systemDefault()),
                        "",
                        s,
                        LocalDateTime.now()
                );
                items.add(feedItem);
            }
            feedItemRepository.saveAll(items);
        }
    }
}
