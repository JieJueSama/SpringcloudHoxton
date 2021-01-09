package com.jiejue.springcloud.controller;


import com.jiejue.springcloud.service.IMessageProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SendMessageController {

    @Resource
    private IMessageProvider messageProvider;

    @GetMapping(value = "/getMessage")
    public String sendMessage(){
        return messageProvider.send();
    }


}
