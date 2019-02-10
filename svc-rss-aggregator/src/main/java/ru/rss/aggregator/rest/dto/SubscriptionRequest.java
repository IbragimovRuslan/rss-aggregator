package ru.rss.aggregator.rest.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.validator.constraints.URL;
import javax.validation.constraints.NotEmpty;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class SubscriptionRequest {
    @URL(message="Not valid URL")
    @NotEmpty(message="URL should not be empty")
    private String rssUrl;
}
