package ru.rss.aggregator.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.PollableChannel;
import ru.rss.aggregator.model.SyndFeedWrapper;
import ru.rss.aggregator.service.message.RssFeedMessageSource;

import java.util.List;

@Configuration
public class MessageConfig {

    @Autowired
    private RssFeedMessageSource rssFeedMessageSource;

    @Bean
    @InboundChannelAdapter(value = "feedChannel", poller = @Poller(maxMessagesPerPoll = "1", fixedDelay = "10000"))
    public MessageSource<List<SyndFeedWrapper>> feedAdapter() {
        return rssFeedMessageSource;
    }

    @Bean(name = "feedChannel")
    public PollableChannel feedChannel() {
        return new QueueChannel();
    }
}
