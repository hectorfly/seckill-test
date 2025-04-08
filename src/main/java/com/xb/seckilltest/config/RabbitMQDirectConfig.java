//package com.xb.seckilltest.config;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//@Component
//public class RabbitMQDirectConfig {
//    private static final String queue01 = "queue01";
//    private static final String queue02 = "queue02";
//    private static final String exChange = "directExChange";
//    private static final String route01 = "route.red";
//    private static final String route02 = "route.blue";
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
//    public DirectExchange directExchange(){
//        return new DirectExchange(exChange);
//    }
//
//    @Bean
//    public Binding binding01(Queue queue01,DirectExchange directExchange){
//        return BindingBuilder.bind(queue01).to(directExchange).with(route01);
//    }
//
//    @Bean
//    public Binding binding02(Queue queue02,DirectExchange directExchange){
//        return BindingBuilder.bind(queue02).to(directExchange).with(route02);
//    }
//}
