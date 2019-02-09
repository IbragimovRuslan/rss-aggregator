package ru.rss.aggregator.rest.api;


import ru.rss.aggregator.rest.api.code.ResponseCode;

public class PositiveResponse<T> extends Response {

    private final T data;

    PositiveResponse(T data) {
        super(ResponseCode.OK);
        this.data = data;
    }

    public ResponseCode getResult() {
        return super.getResult();
    }

    public T getData() {
        return data;
    }
}
