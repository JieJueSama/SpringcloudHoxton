package com.jiejue.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class ApplicationContextConfig {



    //要用自己而写的需要注释
//    @LoadBalanced
    @Bean
    public RestTemplate getResTemplate() {
        return new RestTemplate();
    }
}
//appliocationContext.xml <bean id="" class="">