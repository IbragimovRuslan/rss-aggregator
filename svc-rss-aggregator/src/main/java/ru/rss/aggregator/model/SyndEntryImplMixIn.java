package ru.rss.aggregator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rometools.rome.feed.impl.ObjectBean;
import com.rometools.rome.feed.module.Module;
import org.jdom2.Element;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SyndEntryImplMixIn {
    @JsonIgnore
    private List<Element> foreignMarkup;

    @JsonIgnore
    private Object wireEntry;

    @JsonIgnore
    private ObjectBean objBean;

    @JsonIgnore
    private List<Module> modules;
}
