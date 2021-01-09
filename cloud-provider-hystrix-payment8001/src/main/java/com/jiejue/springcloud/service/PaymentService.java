package com.jiejue.springcloud.service;


import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {


    //正常访问OK
    public String paymentInfo_OK(Integer id){

        return "线程池:" + Thread.currentThread().getName() + "    paymentInfo_OK,id:  " + id + "\t" + "O(∩_∩)O哈哈~";
    }


    //延迟
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public String paymentInfo_TimeOut(Integer id){

//        int age = 10 / 0;
        int tiemNumber = 3;

        try {
            TimeUnit.SECONDS.sleep(tiemNumber);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return "线程池:" + Thread.currentThread().getName() + id + "\t" + "O(∩_∩)O哈哈~" + "耗时：3";
    }

    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池:" + Thread.currentThread().getName() + "    paymentInfo_OK,id:  " + id + "\t8001系统繁忙稍后再试" + "~~~~(>_<)~~~~";
    }


    //=========================服务熔断==================================
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),//时间窗口期        窗口期是经过多久之后恢复一次尝试
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")//失败率达到多少跳闸  百分之60  60%
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if (id < 0){
            throw new RuntimeException("******id 不能负数");
        }
        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName() + "\t" + "调用成功，流水号：" + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id不能是负数，请稍后再试~~~~(>_<)~~~~";
    }
}
