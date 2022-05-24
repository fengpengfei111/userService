package com.example.userservice.service.Impl;

import com.example.userservice.config.RabbitmqConfig;
import com.example.userservice.service.MqService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class MqServiceImpl implements MqService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    RabbitmqConfig rabbitmqConfig;

    @Override
    public Object simpleSend(String message) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        //发消息
        rabbitTemplate.convertAndSend("queue_simple", new Message(message.getBytes(StandardCharsets.UTF_8), messageProperties));
        return "message send : " + message;
    }

    @Override
    public Object workQueueSend(String message) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        //制造多个消息进行发送操作
        for (int i = 0; i < 10; i++) {
            message = message + i;
            rabbitTemplate.convertAndSend("queue_work", new Message(message.getBytes(StandardCharsets.UTF_8), messageProperties));
        }
        return "message send : " + message;
    }

    @Override
    public Object fanOutSend(String message) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        //fanOut模式只往exchange里发送消息。分发到exchange下的所有queue
        rabbitTemplate.convertSendAndReceive("fanOutExchange", "", new Message(message.getBytes(StandardCharsets.UTF_8), messageProperties));
        return "message send : " + message;
    }

    @Override
    public Object sendTopic(String message) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        rabbitTemplate.convertSendAndReceive("exchange_topic", "topic.km.topic", new Message(message.getBytes(StandardCharsets.UTF_8), messageProperties));
        rabbitTemplate.convertSendAndReceive("exchange_topic", "topic.km", new Message(message.getBytes(StandardCharsets.UTF_8), messageProperties));
        return "message send : " + message;
    }

    @Override
    public Object routeSend(String message) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        rabbitTemplate.convertAndSend("directExchange", "info", new Message(message.getBytes(StandardCharsets.UTF_8), messageProperties));
        rabbitTemplate.convertAndSend("directExchange", "waring", new Message(message.getBytes(StandardCharsets.UTF_8), messageProperties));
        rabbitTemplate.convertAndSend("directExchange", "error", new Message(message.getBytes(StandardCharsets.UTF_8), messageProperties));
        return "message send : " + message;

    }

    @Override
    public Object deadSend(String message) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        rabbitTemplate.convertAndSend("simpleExchange", "simple", new Message(message.getBytes(StandardCharsets.UTF_8), messageProperties));
        return "message send : " + message;
    }

}
