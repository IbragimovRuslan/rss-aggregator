package ru.rss.aggregator.rest.api;


import ru.rss.aggregator.rest.api.code.ErrorCode;

public final class RssAPI {
    private RssAPI() {
        throw new RuntimeException();
    }

    public static <T> PositiveResponse<T> positiveResponse(T data) {
        return new PositiveResponse<>(data);
    }

    public static NegativeResponse negativeResponse(ErrorCode errorCode, String errorMessage) {
        return new NegativeResponse(errorCode, errorMessage, null);
    }

    private static NegativeResponse negativeResponse(ErrorCode errorCode, String errorMessage, String details) {
        return new NegativeResponse(errorCode, errorMessage, details);
    }

    public static NegativeResponse negativeResponse(ErrorCode errorCode, String errorMessage, Throwable t) {
        if (t == null) {
            return negativeResponse(errorCode, errorMessage);
        }
        return negativeResponse(errorCode, errorMessage, t.getMessage());
    }

    public static String positiveStringResponse(String data) {
        return "{\"result\": \"ОК\", \"data\":" + data + "}";
    }

    public static String negativeStringResponse(ErrorCode errorCode, String errorMessage, Throwable t) {
        return negativeResponse(errorCode, errorMessage, t).toJsonString();
    }
}
