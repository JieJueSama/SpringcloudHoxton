package com.jiejue.springcloud.controller;


import com.jiejue.springcloud.entities.CommonResult;
import com.jiejue.springcloud.entities.Payment;
import com.jiejue.springcloud.lb.LoadBalancer;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;


@RestController
@Slf4j
public class OrderController {

    //单机版
//    public static final String PAYMENT_URL = "http://localhost:8001";

    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private LoadBalancer loadBalancer;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    DiscoveryClient discoveryClient;


    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        log.info("_____________________创建_____________________");
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id) {
        log.info("_____________________查询_____________________");
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }


    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getPaymentForEntity(@PathVariable("id")Long id){
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);

        if (entity.getStatusCode().is2xxSuccessful()){
            log.info(entity.getStatusCode() + "\t" + entity.getBody());
            return entity.getBody();
        }
        else {
            return new CommonResult<>(444, "操作失败");
        }
    }

    @GetMapping(value = "/consumer/payment/lb")
    public String getPayemtLB(){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances == null || instances.size() <= 0){
            return null;
        }

        ServiceInstance serviceInstance = loadBalancer.instances(instances);
        URI uri = serviceInstance.getUri();


        return restTemplate.getForObject(uri + "/payment/lb", String.class);
    }


    //zipkin
    @GetMapping("/consumer/payment/zipkin")
    public String paymentZipkin(){
        String result = restTemplate.getForObject("http://localhost:8001" + "payment/zipkin/", String.class);
        return result;
    }

}
