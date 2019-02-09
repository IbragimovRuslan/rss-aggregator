package ru.rss.aggregator.model;

import lombok.*;
import ru.rss.aggregator.model.common.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Feed extends AbstractEntity<Long> {

	@Column(name = "feed_url")
	private String feedUrl;

	@Column(name = "title")
	private String title;

	@Column(name = "last_build_date")
	private LocalDateTime lastBuildDate;
}
