package com.microservice.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayserverApplication.class, args);
    }

//    Authentication should be checked
    @Bean
    public RouteLocator bankRouteConfig(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/bankapp/accounts/**")
                        .filters(f -> f.rewritePath("/bankapp/accounts/(?<segment>.*)","/${segment}"))
                        .uri("lb://ACCOUNTS"))
                .route(p -> p
                        .path("/bankapp/loans/**")
                        .filters(f -> f.rewritePath("/bankapp/loans/(?<segment>.*)","/${segment}"))
                        .uri("lb://LOANS"))
                .route(p -> p
                        .path("/bankapp/authentication/**")
                        .filters(f -> f.rewritePath("/bankapp/authentication/(?<segment>.*)","/${segment}"))
                        .uri("lb://AUTHENTICATION")).build();
    }
}
