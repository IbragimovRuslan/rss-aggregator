package ru.rss.aggregator.model;

import lombok.*;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import ru.rss.aggregator.model.common.AbstractEntity;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Subscription extends AbstractEntity<Long> {

	@Column(name = "rss_url")
	private String rssUrl;

	@Column(name = "subscription_cnt")
	private int subscriptionCount;

	@OneToOne(fetch = FetchType.EAGER) //todo: LAZY
	@JoinColumn(name = "feed_id")
	private Feed feed;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "subscription")
	private List<FeedItem> feedItems;
}
