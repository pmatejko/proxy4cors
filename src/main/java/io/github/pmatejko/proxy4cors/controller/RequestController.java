package io.github.pmatejko.proxy4cors.controller;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
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

    @RequestMapping(path = "/**", produces = MediaType.ALL_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<String> proxyRequest(HttpServletRequest servletRequest) {
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
            final var siteResponseHeaders = new LinkedMultiValueMap<>(siteResponse.headers().map());
            final var siteResponseStatus = HttpStatus.valueOf(siteResponse.statusCode());
            final var siteResponseBody = siteResponse.body();

            return new ResponseEntity<>(siteResponseBody, siteResponseHeaders, siteResponseStatus);
        } catch (URISyntaxException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IOException | InterruptedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

// TODO
//    @RequestMapping(path = "/json", method = RequestMethod.POST)
//    public HttpResponse<String> proxyRequest(@RequestBody MyOwnBodyClass requestBody) {
//
//    }

    @RequestMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> proxyRequest() {
        return new ResponseEntity<>("Hi!", HttpStatus.OK);
    }

}
