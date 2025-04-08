//package com.xb.seckilltest.config;
//
//import org.springframework.amqp.core.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//@Component
//public class RabbitMQTopicConfig {
//    private static final String queue01 = "queue01";
//    private static final String queue02 = "queue02";
//    private static final String exchange = "topic_exchange";
//    private static final String route01 = "#.queue.#";
//    private static final String route02 = "*.queue.#";
//
//    @Bean
//    public Queue queue01(){
//        return new Queue(queue01);
//    }
//
//    @Bean
//    public Queue queue02(){
//        return new Queue(queue02);
//    }
//
//    @Bean
//    public TopicExchange topicExchange(){
//        return new TopicExchange(exchange);
//    }
//
//    @Bean
//    public Binding binding01(Queue queue01, TopicExchange topicExchange){
//        return BindingBuilder.bind(queue01).to(topicExchange).with(route01);
//    }
//
//    @Bean
//    public Binding binding02(Queue queue02,TopicExchange topicExchange){
//        return BindingBuilder.bind(queue02).to(topicExchange).with(route02);
//    }
//
//
//
//}
