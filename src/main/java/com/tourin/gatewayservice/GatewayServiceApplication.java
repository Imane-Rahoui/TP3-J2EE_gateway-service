package com.tourin.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    //@Bean         // si on connait le nom des microservices :
    RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {

        return routeLocatorBuilder.routes()
                .route(r->r.path("/customers/**").uri("lb://CUSTOMER-SERVICE")) // les nvelle versions pas d id
                .route(r->r.path("/products/**").uri("lb://PRODUCT-SERVICE"))
                .build();
    }

    @Bean  //100% dynamic
    DiscoveryClientRouteDefinitionLocator definitionLocator(
            ReactiveDiscoveryClient rdc,
            DiscoveryLocatorProperties properties)
    {
        // je dis à spring cloud gateway je connais ni les routes ni les ms
        // ms a chaque fois que tu reçois une request regarde dans l uri tu vas trouver le nom du msi
        // et il va router vers le bon ms
        // mtn : localhost:8888/PRODUCT-SERVICE/products
        return new DiscoveryClientRouteDefinitionLocator(rdc,properties);
        // spring cloud gateway utilise le service RIBOR pour faire l'équilibrage des charges si on a des ms dupliqué
        // mtn il est utilisé implicitement ms on va le config apres
    }
}
