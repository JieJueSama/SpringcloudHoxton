package com.jiejue.springcloud.service;


import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "PaymentFallbackService---------paymentInfo_OK-------------------fallback  ~~~~(>_<)~~~~";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "PaymentFallbackService---------paymentInfo_TimeOut-------------------fallback  ~~~~(>_<)~~~~";
    }
}
