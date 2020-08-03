package com.springcloud.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes().route("path_route2",r->r.path("/user/getByUsername")
                .uri("http://localhost:8201/user/getByUsername")).build();
    }

    /**
     * 注入限流策略 根据请求参数中的username进行限流
     * @return
     */
 /*   @Bean
    KeyResolver userKeyResolver(){
        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("username"));
    }
*/
    /**
     * 注入限流策略 根据访问ip进行限流
     * @return
     */
    @Bean
    KeyResolver ipKeyResolver(){
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }


}
