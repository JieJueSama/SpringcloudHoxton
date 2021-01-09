package com.jiejue.springcloud.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

//Gateway  自定义过滤器
@Component
@Slf4j
public class MyLogGateWayFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.info("*******************************come in MyLogGateWayFilter" + new Date());

        String uname = exchange.getRequest().getQueryParams().getFirst("uname");
        if (uname == null){
            log.info("**************************用户名为null，非法用户，~~~~(>_<)~~~~");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }

        //进入下一层过滤链
        return chain.filter(exchange);
    }



    //加载过滤器的顺序，越小越高
    @Override
    public int getOrder() {
        return 0;
    }
}
