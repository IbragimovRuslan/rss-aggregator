package ru.rss.aggregator.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rss.aggregator.model.Subscription;
import ru.rss.aggregator.repository.SubscriptionRepository;
import ru.rss.aggregator.rest.api.Response;
import ru.rss.aggregator.rest.api.RssAPI;
import ru.rss.aggregator.rest.api.code.ErrorCode;
import ru.rss.aggregator.rest.dto.SubscriptionRequest;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class RssFeedController {

    private final SubscriptionRepository subscriptionRepository;

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
