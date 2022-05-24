package com.example.userservice.controller;


import com.example.userservice.service.Impl.MqServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class MqController {
    @Autowired
    MqServiceImpl mqService;

    @RequestMapping("/simpleSend")
    //直接发送到队列
    public  Object simpleSend(@RequestBody String message) throws Exception {
        return mqService.simpleSend(message);
    }

    @RequestMapping("/workQueueSend")
    //发送到所有监听该队列的消费
    public Object workQueueSend(@RequestBody String message) throws Exception {
        return mqService.workQueueSend(message);
    }

    @RequestMapping("/fanOutSend")
    // 发送到fanOutSendExchange。消息将往该exchange下的所有queue转发
    public Object fanOutSend(@RequestBody String message) throws Exception {
        return mqService.fanOutSend(message);
    }

    @RequestMapping("/sendTopic")
    public Object sendTopic(@RequestBody String message) {
        //发送到topicExchange。exchange转发消息时，会往routingKey匹配的queue发送，*代表一个单词，#代表0个或多个单词。
        return mqService.sendTopic(message);
    }

    @RequestMapping("/routeSend")
    public Object routeSend(@RequestBody String message) {
       //发送到directExchange。exchange转发消息时，会往routingKey匹配的queue发送
        return mqService.routeSend(message);
    }

    @RequestMapping("/deadSend")
    public Object deadSend(@RequestBody String message) {
        //发送到directExchange。exchange转发消息时，会往routingKey匹配的queue发送
        return mqService.deadSend(message);
    }


}
