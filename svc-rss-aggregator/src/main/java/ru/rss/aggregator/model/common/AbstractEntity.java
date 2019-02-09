package ru.rss.aggregator.model.common;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractEntity<V> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(updatable = false, nullable = false)
    private V id;

    public V getId() {
        return id;
    }

    public void setId(V id) {
        this.id = id;
    }
}