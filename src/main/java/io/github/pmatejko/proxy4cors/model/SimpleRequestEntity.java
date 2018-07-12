package io.github.pmatejko.proxy4cors.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class SimpleRequestEntity {
    private final String url;
    private final String method;
    private final String body;
    private final Map<String, List<String>> headers;

    @JsonCreator
    public SimpleRequestEntity(@JsonProperty(value = "url") String url,
                               @JsonProperty(value = "method", defaultValue = "GET") String method,
                               @JsonProperty(value = "body") String body,
                               @JsonProperty(value = "headers") Map<String, List<String>> headers) {
        this.url = url;
        this.method = method;
        this.body = body;
        this.headers = headers;
    }


    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public String getBody() {
        return body;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }
}
