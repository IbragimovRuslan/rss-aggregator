package ru.rss.aggregator.model.common;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class ElasticEntity implements Serializable {

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
