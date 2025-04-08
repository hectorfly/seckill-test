package com.xb.seckilltest.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


//@Component
//public class RabbitMQFanOutConfig {
//
//    private static final String queue01 = "queue01";
//    private static final String queue02 = "queue02";
//    private static final String fanoutChange = "fanoutEXchange01";
//
//    @Bean
//    public Queue queue(){
//        return new Queue("queue",true);//true为是否开启持久化
//    }
//    @Bean
//    public Queue queue01(){
//        return new Queue(queue01,true);//true为是否开启持久化
//    }
//    @Bean
//    public Queue queue02(){
//        return new Queue(queue02,true);//true为是否开启持久化
//    }
//    @Bean
//    public FanoutExchange fanoutEXchange01(){
//        return new FanoutExchange(fanoutChange);
//    }
//
//    @Bean
//    public Binding binding01(Queue queue01,FanoutExchange fanoutEXchange01){
//        return BindingBuilder.bind(queue01).to(fanoutEXchange01);
//    }
//
//    @Bean
//    public Binding binding02(Queue queue02,FanoutExchange fanoutEXchange01){
//        return BindingBuilder.bind(queue02).to(fanoutEXchange01);
//    }
//


//}
