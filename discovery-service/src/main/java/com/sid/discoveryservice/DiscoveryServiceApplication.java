package com.sid.discoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServiceApplication.class, args);
    }
//   configuration du localisation static
//    @Bean
//    RouteLocator gatewayRoutes (RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(r->r.path("/customers/**").uri("lb://CUSTOMER-SERVICE").id("r1"))
//                .route(r->r.path("/products/**").uri("lb://INVENTORY-SERVICE").id("r2"))
//                .build();
//    }


}
