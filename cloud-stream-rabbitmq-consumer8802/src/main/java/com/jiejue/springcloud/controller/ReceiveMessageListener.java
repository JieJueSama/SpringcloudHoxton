package com.jiejue.springcloud.controller;


import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;

@Component
@EnableBinding(Sink.class)
public class ReceiveMessageListener {


    @Resource
    private String serverPort;


    @StreamListener(Sink.INPUT)
    public void input(Message<String> message){
        System.out.println("消费者接受消息" + message.getPayload() + "\t port:" + serverPort);
    }



}
