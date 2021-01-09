package com.jiejue.springcloud.service.impl;

import cn.hutool.core.lang.UUID;
import com.jiejue.springcloud.service.IMessageProvider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;



@Slf4j
@EnableBinding(Source.class)    //定义消息的推送通道
public class IMessageProviderImpl implements IMessageProvider {



    //消息发送通道
    @Resource
    private MessageChannel output;

    @Override
    public String send() {

        String serial = UUID.randomUUID().toString();
        output.send(MessageBuilder.withPayload(serial).build());
        log.info("****************************************serial=" + serial);
        return null;
    }
}
