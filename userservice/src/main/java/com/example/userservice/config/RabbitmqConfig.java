package com.example.userservice.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitmqConfig {

    // 配置一个工作模型队列
    @Bean
    public Queue queueSimple() {
        return new Queue("queue_simple");
    }

    @Bean
    public Queue queueWork() {
        return new Queue("queue_work");
    }


    // 准备一个交换机
    @Bean
    public FanoutExchange exchangeFanOut() {
        return new FanoutExchange("fanOutExchange");
    }

    // 将交换机和队列进行绑定
    @Bean
    public Binding bindingExchange1() {
        return BindingBuilder.bind(queueSimple()).to(exchangeFanOut());
    }

    // 将交换机和队列进行绑定
    @Bean
    public Binding bindingExchange2() {
        return BindingBuilder.bind(queueWork()).to(exchangeFanOut());
    }

    //topic

    @Bean
    public Queue queueTopic1() {
        return new Queue("queue_topic1");
    }

    @Bean
    public Queue queueTopic2() {
        return new Queue("queue_topic2");
    }

    @Bean
    public TopicExchange exchangeTopic() {
        return new TopicExchange("exchange_topic");
    }

    /**
     * *(星号)：可以(只能)匹配一个单词
     * #(井号)：可以匹配多个单词(或者零个)
     */

    @Bean
    public Binding bindingTopic1() {
        //# 匹配多个
        return BindingBuilder.bind(queueTopic1()).to(exchangeTopic()).with("topic.#");
    }

    @Bean
    public Binding bindingTopic2() {
        // * 匹配一个
        return BindingBuilder.bind(queueTopic2()).to(exchangeTopic()).with("topic.*");

    }

    //route路由

    @Bean
    public Queue queue1() {
        return new Queue("routing-queue1");
    }

    @Bean
    public Queue queue2() {
        return new Queue("routing-queue2");
    }

    @Bean
    public Queue queue3() {
        return new Queue("routing-queue3");
    }

    //声明交换机，路由模式 DirectExchange
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }

    //建立队列与交换机的关系
    @Bean
    public Binding bindQueue1ToDirectExchange() {
        return BindingBuilder.bind(queue1()).to(directExchange()).with("info");
    }

    @Bean
    public Binding bindQueue2ToDirectExchange() {
        return BindingBuilder.bind(queue2()).to(directExchange()).with("waring");
    }

    @Bean
    public Binding bindQueue3ToDirectExchange() {
        return BindingBuilder.bind(queue3()).to(directExchange()).with("error");
    }






    @Bean
    public Exchange deadLetterExchange() {
        return new DirectExchange("DXLExchange", true, false);
    }


    @Bean
    public Queue simpleQueue() {
        return QueueBuilder
                .durable("simpleQueue")
                //声明该队列的死信消息发送到的 交换机 （队列添加了这个参数之后会自动与该交换机绑定，并设置路由键，不需要开发者手动设置)
                .withArgument("x-dead-letter-exchange", "DXLExchange")
                //声明该队列死信消息在交换机的 路由键
                .withArgument("x-dead-letter-routing-key", "dead-letter-routing-key")
                .build();
    }

    /**
     * 普通交换机
     * @return
     */
    @Bean
    public DirectExchange simpleExchange() {
        return new DirectExchange("simpleExchange",true, false);
    }

    /**
     * 普通队列与交换机绑定
     *
     * @param simpleQueue    普通队列名
     * @param simpleExchange 普通交换机名
     * @return
     */
    @Bean
    public Binding binding(Queue simpleQueue, DirectExchange simpleExchange) {
        return BindingBuilder.bind(simpleQueue).to(simpleExchange).with("simple");
    }


    /**
     * 普通队列的死信消息 路由的队列
     * 普通队列simpleQueue的死信投递到死信交换机`dead-letter-exchange`后再投递到该队列
     * 用这个队列来接收simple-queue的死信消息
     *
     * @return
     */
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable("deadLetterQueue").build();
    }

    /**
     * 死信队列绑定死信交换机
     *
     * @param deadLetterQueue      simple-queue对应的死信队列
     * @param deadLetterExchange 通用死信交换机
     * @return
     */
    @Bean
    public Binding deadLetterBinding(Queue deadLetterQueue, Exchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with("dead-letter-routing-key").noargs();
    }

//    @Bean
//    //设置为多实例的,每次注入都使用不同的示例
//    @Scope("prototype")
//    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory){
//        RabbitTemplate rabbitTemplate = new RabbitTemplate();
//        rabbitTemplate.setConnectionFactory(connectionFactory);
//        return rabbitTemplate;
//    }

}


