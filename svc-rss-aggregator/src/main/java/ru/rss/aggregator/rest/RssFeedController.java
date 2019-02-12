package ru.rss.aggregator.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.rss.aggregator.model.FeedItem;
import ru.rss.aggregator.model.Subscription;
import ru.rss.aggregator.repository.FeedItemRepository;
import ru.rss.aggregator.repository.SubscriptionRepository;
import ru.rss.aggregator.repository.elasticsearch.ItemRepository;
import ru.rss.aggregator.rest.api.Response;
import ru.rss.aggregator.rest.api.RssAPI;
import ru.rss.aggregator.rest.api.code.ErrorCode;
import ru.rss.aggregator.rest.dto.SubscriptionRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class RssFeedController {

    private final SubscriptionRepository subscriptionRepository;
    private final ItemRepository itemRepository;
    private final FeedItemRepository feedItemRepository;

    @GetMapping(value = "/containing/in/{param}")
    public Response findByParamContaining(@PathVariable String param) {
        return RssAPI.positiveResponse(itemRepository.findByJsonItemContaining(param));
    }

    @GetMapping(value = "/containing/{field}/{substring}")
    public Response findByTitle(@PathVariable String field, @PathVariable String substring) {
        substring = "%" + substring + "%";
        List<FeedItem> feedItems = feedItemRepository.getByFieldContainingString(field, substring);
        List<String> jsonFeedItems = feedItems.stream().map(FeedItem::getJsonItem).collect(Collectors.toList());
        return RssAPI.positiveResponse(jsonFeedItems);
    }

    @PostMapping("/subscribe")
    public Response register(@RequestBody(required = true) @Valid SubscriptionRequest subscriptionRequest) {

        String rssUrl = subscriptionRequest.getRssUrl().trim().toLowerCase();
        if (rssUrl.endsWith("/"))
            rssUrl = rssUrl.substring(0, rssUrl.length() - 1);

        Subscription subscription = subscriptionRepository.findByRssUrl(rssUrl);

        if (Objects.nonNull(subscription)) {
            return RssAPI.negativeResponse(ErrorCode.ILLEGAL_PARAMETER, "Subscription with given URL [" + rssUrl + "] already exist");
        } else {
            subscription = new Subscription();
            subscription.setRssUrl(rssUrl);
            subscriptionRepository.save(subscription);
            return RssAPI.positiveResponse(subscription.getId());
        }
    }
}
