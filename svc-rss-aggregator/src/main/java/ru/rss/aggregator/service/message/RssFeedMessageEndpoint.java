package ru.rss.aggregator.service.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.util.StringUtils;
import ru.rss.aggregator.model.*;
import ru.rss.aggregator.model.elastic.Item;
import ru.rss.aggregator.repository.FeedItemRepository;
import ru.rss.aggregator.repository.FeedRepository;
import ru.rss.aggregator.repository.SubscriptionRepository;
import ru.rss.aggregator.repository.elasticsearch.ItemRepository;
import ru.rss.aggregator.utils.DateTimeUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@MessageEndpoint
@AllArgsConstructor
public class RssFeedMessageEndpoint {
    private static final Logger logger = LoggerFactory.getLogger(RssFeedMessageEndpoint.class);
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper().addMixIn(SyndEntryImpl.class, SyndEntryImplMixIn.class);

    private final ItemRepository itemRepository;
    private final FeedItemRepository feedItemRepository;
    private final FeedRepository feedRepository;
    private final SubscriptionRepository subscriptionRepository;

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

            Subscription subscription = subscriptionRepository.findByRssUrl(syndFeedWrapper.getRssUrs());
            if (subscription == null) {
                logger.error("No subscription found for URL: " + syndFeedWrapper.getRssUrs());
                continue;
            }

            // There's no easy way to update feed if it has no lastBuildDate. We're not replicating such feeds.
            if (Objects.isNull(syndFeed.getPublishedDate())) {
                logger.info("Skip Feed {} - has no lastBuildDate, ", feedUrl);
                continue;
            }
            LocalDateTime publishedDate = DateTimeUtils.dateToLocalDateTime(syndFeed.getPublishedDate());

            Feed feed = subscription.getFeed();
            if (feed != null) {
                if (feed.getLastBuildDate().compareTo(publishedDate) == 0) {
                    logger.info("Found Feed {} - has actual date and will not be replaced with a new", feedUrl);
                    continue;
                } else {
                    feed.setLastBuildDate(publishedDate);
                    feedRepository.save(feed);
                }
            } else {
                feed = new Feed(feedUrl, syndFeed.getTitle(), publishedDate);
                subscription.setFeed(feed);
                feedRepository.save(feed);
                subscriptionRepository.save(subscription);
            }

            List<FeedItem> feedItems = new ArrayList<>();
            List<Item> elasticItems = new ArrayList<>();

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

                String jsonItemStr = OBJECT_MAPPER.writeValueAsString(syndEntry);
                FeedItem feedItem = new FeedItem(jsonItemStr, subscription, LocalDateTime.now());

                feedItems.add(feedItem);
                elasticItems.add(new Item(jsonItemStr));
            }

            feedItemRepository.saveAll(feedItems);
            itemRepository.saveAll(elasticItems);
        }
    }
}
