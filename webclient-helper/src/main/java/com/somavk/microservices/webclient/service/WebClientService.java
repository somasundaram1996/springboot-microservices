package com.somavk.microservices.webclient.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebClientService {

    private final WebClient webClient;

    private Function<UriBuilder, URI> buildUri(String uriTemplate, Map<String, String> pathParams, Map<String, String> queryParams) {
        return uriBuilder -> {
            UriBuilder builder = uriBuilder.path(uriTemplate);
            if (queryParams != null) {
                queryParams.forEach(builder::queryParam);
            }
            return builder.build(pathParams);
        };
    }

    private <T> Mono<T> handleError(String method, String uri, Throwable ex) {
        log.error("Fallback for {} {}: {}", method, uri, ex.toString());
        return Mono.error(new RuntimeException("Service temporarily unavailable: " + ex.getMessage()));
    }

    public <T> Mono<T> get(String uriTemplate, Map<String, String> pathParams, Map<String, String> queryParams, Class<T> responseType) {
        return webClient.get()
                .uri(buildUri(uriTemplate, pathParams, queryParams))
                .retrieve()
                .bodyToMono(responseType);
    }

    public <T, R> Mono<R> post(String uriTemplate, Map<String, String> pathParams, Map<String, String> queryParams, T requestBody, Class<R> responseType) {
        return webClient.post()
                .uri(buildUri(uriTemplate, pathParams, queryParams))
                .body(Mono.just(requestBody), requestBody.getClass())
                .retrieve()
                .bodyToMono(responseType);
    }

    public <T, R> Mono<R> put(String uriTemplate, Map<String, String> pathParams, Map<String, String> queryParams, T requestBody, Class<R> responseType) {
        return webClient.put()
                .uri(buildUri(uriTemplate, pathParams, queryParams))
                .body(Mono.just(requestBody), requestBody.getClass())
                .retrieve()
                .bodyToMono(responseType);
    }

    @CircuitBreaker(name = "defaultCB", fallbackMethod = "deleteFallback")
    public <T> Mono<T> delete(String uriTemplate, Map<String, String> pathParams, Map<String, String> queryParams, Class<T> responseType) {
        return webClient.delete()
                .uri(buildUri(uriTemplate, pathParams, queryParams))
                .retrieve()
                .bodyToMono(responseType);
    }

    // Fallbacks
}
