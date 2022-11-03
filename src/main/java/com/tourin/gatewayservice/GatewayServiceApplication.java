package com.tourin.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    @Bean         // si on connait le nom des microservices :
    RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {

        return routeLocatorBuilder.routes()
                .route(r->r.path("/customers/**").uri("lb://CUSTOMER-SERVICE")) // les nvelle versions pas d id
                .route(r->r.path("/products/**").uri("lb://PRODUCT-SERVICE"))
                .build();
    }
}
