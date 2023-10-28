package com.example.demoreactive.aspects;

import com.example.demoreactive.exceptions.InvalidHeaderException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Aspect
@Component
public class HeaderValidationAspect {

    @Around("@annotation(com.example.demoreactive.aspects.HeaderValidator)")
    @Order(1)
    public Object validateHeader(ProceedingJoinPoint joinPoint) throws Throwable {
        ServerHttpRequest request = ((ServerWebExchange) joinPoint.getArgs()[0]).getRequest();
        ServerHttpResponse response = ((ServerWebExchange) joinPoint.getArgs()[0]).getResponse();

        if (request.getHeaders().containsKey("x-header")) {
            String headerValue = request.getHeaders().getFirst("x-header");
            if (validHeader(headerValue)) {
                Mono<?> result = (Mono<?>) joinPoint.proceed();
                return result
                        .doOnNext( value -> response.getHeaders().add("x-response", value.toString()));
            } else {
                throw new InvalidHeaderException("Invalid header");
            }
        } else {
            throw new InvalidHeaderException("Header not found");
        }
    }

    private boolean validHeader(String headerValue) {
        return headerValue.equals("x-value");
    }
}
