package ru.rss.aggregator.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rss.aggregator.model.Feed;
import ru.rss.aggregator.model.Subscription;
import ru.rss.aggregator.repository.SubscriptionRepository;
import ru.rss.aggregator.rest.api.ResponseMap;
import ru.rss.aggregator.rest.dto.SubscriptionRequest;

import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class RssFeedController {

    private final SubscriptionRepository subscriptionRepository;

    @PostMapping("/subscribe")
    public ResponseEntity<Map<String, Object>> register(@RequestBody(required = true) @Valid SubscriptionRequest subscriptionRequest) {

        String rssUrl = subscriptionRequest.getRssUrl().trim().toLowerCase();
        if (rssUrl.endsWith("/"))
            rssUrl = rssUrl.substring(0, rssUrl.length() - 1);

        Subscription subscription = subscriptionRepository.findByRssUrl(rssUrl);

        if (Objects.nonNull(subscription)) {
            String errMsg = "Subscription with given URL [" + rssUrl + "] already exist";
            return new ResponseEntity<>(ResponseMap.mapError(errMsg), HttpStatus.BAD_REQUEST);
        } else {
            subscription = new Subscription();
            subscription.setRssUrl(rssUrl);
            subscriptionRepository.save(subscription);
            return new ResponseEntity<>(ResponseMap.mapOK("Ok"),HttpStatus.OK);
       }
    }
}
