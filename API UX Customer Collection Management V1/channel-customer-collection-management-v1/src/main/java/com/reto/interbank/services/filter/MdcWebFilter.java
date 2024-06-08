package com.reto.interbank.services.filter;

import org.slf4j.MDC;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class MdcWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        String traceId = serverWebExchange.getRequest().getHeaders().getFirst("traceId");
        return webFilterChain.filter(serverWebExchange)
                .contextWrite(ctx -> ctx.put("traceId", traceId))
                .doOnEach(signal -> {
                    if (signal.getContextView().hasKey("traceId")) {
                        MDC.put("traceId", signal.getContextView().get("traceId"));
                    }
                });
    }

}