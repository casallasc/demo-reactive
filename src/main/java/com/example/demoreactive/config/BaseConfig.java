package com.example.demoreactive.config;

import com.example.demoreactive.model.Config;
import com.example.demoreactive.services.GreetingService;
import com.example.demoreactive.services.HelloService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class BaseConfig {

    @Value("${secret-id}")
    private String secretId;

    @Value("${api-key}")
    private String apiKey;

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl("http://test-lbk.free.beeceptor.com")
                .filter(createDynamicHeaderFilter())
                .build();
    }

    @Bean
    public GreetingService apiService(WebClient webClient) {
        return new GreetingService(webClient);
    }

    @Bean
    public HelloService helloService() {
        return new HelloService();
    }

    @Bean
    public Config config() {
        return new Config(secretId, apiKey);
    }

    private ExchangeFilterFunction createDynamicHeaderFilter() {
        return ExchangeFilterFunction.ofRequestProcessor(request -> {
            return Mono.just(ClientRequest.from(request)
                    .header("x-custom-header", "x-custom-value-" +
                            System.currentTimeMillis())
                    .build());
        });
    }
}
