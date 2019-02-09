package ru.rss.aggregator.model;

import ru.rss.aggregator.model.common.AbstractEntity;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedItemShort extends AbstractEntity<Long> {

	private String subscriptionId;
	private String guid;
	private String title;

}
