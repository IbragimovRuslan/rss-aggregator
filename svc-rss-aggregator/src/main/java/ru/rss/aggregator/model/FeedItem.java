package ru.rss.aggregator.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.rss.aggregator.model.common.AbstractEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FeedItem extends AbstractEntity<Long> {

	@Column(name = "guid")
	private String guid;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "author")
	private String author;

	@Column(name = "publication_date")
	private LocalDateTime publicationDate;

	@Column(name = "json_item")
	private String jsonItem;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subscription_id")
	private Subscription subscription;

	@CreationTimestamp
	private LocalDateTime createdAt;
}
