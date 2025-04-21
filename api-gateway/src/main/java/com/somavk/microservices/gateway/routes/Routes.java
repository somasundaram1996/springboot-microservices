package com.somavk.microservices.gateway.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.TokenRelayFilterFunctions.tokenRelay;

@Configuration
public class Routes {

    @Value("${routeConfig.product.url}")
    private String productUrl;
    @Value("${routeConfig.order.url}")
    private String orderUrl;
    @Value("${routeConfig.inventory.url}")
    private String inventoryUrl;
    @Bean
    public RouterFunction<ServerResponse> productServiceRoute() {
        return GatewayRouterFunctions.route("PRODUCT_SERVICE")
                .route(RequestPredicates.path("/api/v1/products/**"), HandlerFunctions.http(productUrl))
                .filter(tokenRelay())
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceRoute() {
        return GatewayRouterFunctions.route("ORDER_SERVICE")
                .route(RequestPredicates.path("/api/v1/orders/**"), HandlerFunctions.http(orderUrl))
                .filter(tokenRelay())
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceRoute() {
        return GatewayRouterFunctions.route("INVENTORY_SERVICE")
                .route(RequestPredicates.path("/api/v1/inventories/**"), HandlerFunctions.http(inventoryUrl))
                .filter(tokenRelay())
                .build();
    }
}
