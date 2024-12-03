package com.sparta.msa_exam.gateway;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class CustomPostFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            org.springframework.cloud.gateway.filter.GatewayFilterChain chain
    ) {
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            if (!exchange.getResponse().isCommitted()) { // 응답이 이미 커밋되었는지 확인
                URI requestUrl = exchange.getAttribute(
                        ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR
                ); // 요청 URL을 Gateway 속성에서 가져옴
                HttpHeaders headers = exchange.getResponse().getHeaders();
                headers.add(
                        "Server-Port",
                        String.valueOf(requestUrl != null ? requestUrl.getPort() : 0)
                ); // 요청 URL에서 포트를 가져와 헤더에 추가
            }
        }));
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
