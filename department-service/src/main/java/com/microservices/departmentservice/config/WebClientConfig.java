package com.microservices.departmentservice.config;

import com.microservices.departmentservice.client.EmployeeClient;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.web.exchanges.HttpExchange;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpExchangeAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

    @Autowired
    private LoadBalancedExchangeFilterFunction loadBalancedExchangeFilterFunction;

    @Bean
    public WebClient employeeWebClient(){
        return WebClient
                .builder()
                .baseUrl("http://employee-service")
                .filter(loadBalancedExchangeFilterFunction)
                .build();
    }

//    @SneakyThrows
    @Bean
    public EmployeeClient employeeClient(){
        HttpServiceProxyFactory httpServiceProxyFactory
                = HttpServiceProxyFactory
                    .builderFor(WebClientAdapter.create(employeeWebClient()))
                    .build();
        return httpServiceProxyFactory.createClient(EmployeeClient.class);
    }

//    @Bean
//    public EmployeeClient employeeClient() {
//        HttpServiceProxyFactory httpServiceProxyFactory
//                = HttpServiceProxyFactory
//                .builder(WebClientAdapter.forClient(employeeWebClient()))
//                .build();
//        return httpServiceProxyFactory.createClient(EmployeeClient.class);
//    }
}
