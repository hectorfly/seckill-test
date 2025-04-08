package com.xb.seckilltest.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConfig {
    private static final String queue01 = "queue01";
    private static final String exchange = "topicExchange";
    private static final String route = "#.queue.#";

    @Bean
    public Queue queue01(){
        return new Queue(queue01);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding binding01(Queue queue01, TopicExchange topicExchange){
        return BindingBuilder.bind(queue01).to(topicExchange).with(route);
    }

}
