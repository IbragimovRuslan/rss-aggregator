package ru.rss.aggregator.rest.api;

import ru.rss.aggregator.rest.api.code.ResponseCode;

public abstract class Response {

    private final ResponseCode result;

    Response(ResponseCode result) {
        this.result = result;
    }

    ResponseCode getResult() {
        return result;
    }
}
