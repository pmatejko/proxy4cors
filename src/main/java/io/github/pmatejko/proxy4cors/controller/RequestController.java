package io.github.pmatejko.proxy4cors.controller;

import io.github.pmatejko.proxy4cors.model.ErrorHttpResponse;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
public class RequestController {

    @RequestMapping(path = "/**")
    public HttpResponse<String> proxyRequest(HttpServletRequest servletRequest) {
        try {
            final var headersList = new ArrayList<String>();
            servletRequest.getHeaderNames()
                    .asIterator()
                    .forEachRemaining(headerName -> {
                        headersList.add(headerName);
                        headersList.add(servletRequest.getHeader(headerName));
                    });

            final var uri = new URI(servletRequest.getRequestURI().substring(1));
            final var body = servletRequest.getReader()
                    .lines()
                    .collect(Collectors.joining(System.lineSeparator()));

            final var bodyPublisher = "".equals(body)
                    ? HttpRequest.BodyPublisher.noBody()
                    : HttpRequest.BodyPublisher.fromString(body);
            final var proxyRequest = HttpRequest.newBuilder()
                    .uri(uri)
                    .headers(headersList.toArray(new String[0]))
                    .method(servletRequest.getMethod(), bodyPublisher)
                    .timeout(Duration.ofSeconds(15L))
                    .build();

            final var siteResponse = HttpClient.newHttpClient()
                    .send(proxyRequest, HttpResponse.BodyHandler.asString());

            siteResponse.headers().map().forEach((s, strings) -> System.out.print(s + ": " + strings + ", "));
            return siteResponse;
        } catch (URISyntaxException e) {
            return new ErrorHttpResponse<>(e.getMessage(), 400);
        } catch (IOException | InterruptedException e) {
            return new ErrorHttpResponse<>(e.getMessage(), 500);
        }
    }

// TODO
//    @RequestMapping(path = "/json", method = RequestMethod.POST)
//    public HttpResponse<String> proxyRequest(@RequestBody MyOwnBodyClass requestBody) {
//
//    }

}
