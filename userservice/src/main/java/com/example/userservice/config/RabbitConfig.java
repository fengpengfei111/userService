package com.example.userservice.config;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;


@Component
public class RabbitConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (!ack) {
            // 根据具体的业务进行相应的处理
            System.out.println("消息发送失败，进行容错处理");
        } else {
            System.out.println("消息发送成功");
        }
        System.out.println("消息发送到交换机时的回调函数, ack：" + ack + "  消息体：" + cause);
    }

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        //根据具体的业务对异常进行处理，自行判断是否消息可以丢弃
        if (AMQP.NO_ROUTE == returnedMessage.getReplyCode()) {
            System.out.println("【队列】 交换机路由到队列失败====" + returnedMessage.getMessage());
        }
        System.out.println("消息从交换机发送到队列时失败的回调函数, 调用失败！！！");
    }
}
