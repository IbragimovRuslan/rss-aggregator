package ru.rss.aggregator.model;

import lombok.*;
import ru.rss.aggregator.model.common.AbstractEntity;

import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StandartEntity extends AbstractEntity<Long> {
    private String name;
}
