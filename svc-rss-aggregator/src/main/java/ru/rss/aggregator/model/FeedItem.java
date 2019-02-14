package ru.rss.aggregator.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.rss.aggregator.model.common.AbstractEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@TypeDef(
		name = "pgsql_jsonb",
		typeClass = JsonBinaryType.class
)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, exclude = "subscription")
public class FeedItem extends AbstractEntity<Long> {

	@Column(name = "item")
	@Type(type = "pgsql_jsonb")
	private String jsonItem;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subscription_id")
	private Subscription subscription;

	@CreationTimestamp
	private LocalDateTime createdAt;
}
