package ru.rss.aggregator.rest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.rss.aggregator.rest.api.code.ErrorCode;
import ru.rss.aggregator.rest.api.code.ResponseCode;

public class NegativeResponse extends Response {

    private final ErrorCode errorCode;

    private final String message;

    private final String details;

    NegativeResponse(ErrorCode errorCode, String message, String details) {
        super(ResponseCode.FAIL);
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
    }

    public ResponseCode getResult() {
        return super.getResult();
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public String toJsonString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("result", getResult().toString());
        node.put("code", errorCode.name());
        node.put("message", message);
        node.put("details", details);
        return node.toString();
    }
}
