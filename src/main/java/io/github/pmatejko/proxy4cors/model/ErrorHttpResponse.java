package io.github.pmatejko.proxy4cors.model;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpHeaders;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

import javax.net.ssl.SSLParameters;
import java.net.URI;
import java.util.Optional;

public class ErrorHttpResponse<T> extends HttpResponse<T> {
    private final T body;
    private final int statusCode;
    private final HttpRequest request;

    public ErrorHttpResponse(T body, int statusCode) {
        this(body, statusCode, null);
    }

    public ErrorHttpResponse(T body, int statusCode, HttpRequest request) {
        this.body = body;
        this.statusCode = statusCode;
        this.request = request;
    }

    @Override
    public int statusCode() {
        return statusCode;
    }

    @Override
    public HttpRequest request() {
        return request;
    }

    @Override
    public Optional<HttpResponse<T>> previousResponse() {
        return Optional.empty();
    }

    @Override
    public HttpHeaders headers() {
        return null;
    }

    @Override
    public T body() {
        return body;
    }

    @Override
    public SSLParameters sslParameters() {
        return null;
    }

    @Override
    public URI uri() {
        return null;
    }

    @Override
    public HttpClient.Version version() {
        return null;
    }
}
