package io.github.pmatejko.proxy4cors.model;

public class Request {
    private final String url;
    private final String httpMethod;

    public Request(String url, String httpMethod) {
        this.url = url;
        this.httpMethod = httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public String getHttpMethod() {
        return httpMethod;
    }
}
