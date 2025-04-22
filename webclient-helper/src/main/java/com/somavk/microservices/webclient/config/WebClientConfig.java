package com.somavk.microservices.webclient.config;

import com.somavk.microservices.webclient.service.WebClientService;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.opentelemetry.api.trace.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Configuration
public class WebClientConfig {
    @Value("${gateway.url}")
    private String gatewayHost;
    @Autowired
    private Tracer tracer;

    @Bean
    public WebClient webClient(@Autowired @Qualifier("gjwtRepository") ReactiveClientRegistrationRepository reactiveClientRegistrationRepository) {
        InMemoryReactiveOAuth2AuthorizedClientService clientService = new InMemoryReactiveOAuth2AuthorizedClientService(reactiveClientRegistrationRepository);
        ReactiveOAuth2AuthorizedClientManager clientManager = new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(reactiveClientRegistrationRepository, clientService);
        ServerOAuth2AuthorizedClientExchangeFilterFunction filter = new ServerOAuth2AuthorizedClientExchangeFilterFunction(clientManager);
        filter.setDefaultClientRegistrationId("jwt");
        return  WebClient.builder()
                .baseUrl(gatewayHost)
                .clientConnector(new ReactorClientHttpConnector(HttpClient
                        .create(ConnectionProvider.create("fixedConnectionPool", 10))
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                        .doOnConnected(connection -> connection
                                .addHandlerFirst("readTimeoutHandler", new ReadTimeoutHandler(10))
                                .addHandlerFirst("writeTimeoutHandler", new WriteTimeoutHandler(10)))))
                .filter(filter)
                .build();
    }

    @Bean ("gjwtRepository")
    public ReactiveClientRegistrationRepository reactiveClientRegistrationRepository(
            @Value("${spring.security.oauth2.client.provider.jwt.token-uri}") String tokenUri,
            @Value("${spring.security.oauth2.client.registration.jwt.client-id}") String clientId,
            @Value("${spring.security.oauth2.client.registration.jwt.client-secret}") String clientSecret
    ) {
        ClientRegistration clientRegistration = ClientRegistration
                .withRegistrationId("jwt")
                .clientId(clientId)
                .tokenUri(tokenUri)
                .clientSecret(clientSecret)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .build();

        return new InMemoryReactiveClientRegistrationRepository(clientRegistration);
    }

    @Bean
    public WebClientService webClientService(@Autowired WebClient webClient) {
        return new WebClientService(webClient);
    }
}

