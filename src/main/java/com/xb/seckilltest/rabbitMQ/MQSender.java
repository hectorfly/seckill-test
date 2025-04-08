package com.xb.seckilltest.rabbitMQ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class MQSender {

    @Resource
    RabbitTemplate rabbitTemplate;

//    public void sendMeg(Object msg){
//        log.info("MQSender 发送了一条新消息：" + msg);
//        rabbitTemplate.convertAndSend( "queue",msg);
//    }
//
//    public void sendMegToFanoutEXchange01(Object msg){
//        log.info("MQSender 发送了一条新消息：" + msg);
//        rabbitTemplate.convertAndSend( "fanoutEXchange01","",msg);//一定要加空的路由规则，FanoutEXchange广播模式
//    }
//
//    public void sendMegToDirectEXchange01(Object msg){
//        log.info("MQSender 发送了一条Direct red新消息：" + msg);
//        rabbitTemplate.convertAndSend( "directExChange","route.red",msg);//发送方要指定路由
//    }
//
//    public void sendMegToDirectEXchange02(Object msg){
//        log.info("MQSender 发送了一条Direct blue新消息：" + msg);
//        rabbitTemplate.convertAndSend( "directExChange","route.blue",msg);//发送方要指定路由
//    }
//
//    public void sendMegToTopicEXchange01(Object msg){
//        log.info("MQSender 发送了一条Topic01新消息：" + msg);
//        rabbitTemplate.convertAndSend( "topic_exchange","queue.ii.org",msg);//发送方要根据路由规则发送给指定队列，匹配两个
//    }
//
//    public void sendMegToTopicEXchange02(Object msg){
//        log.info("MQSender 发送了一条Topic02新消息：" + msg);
//        rabbitTemplate.convertAndSend( "topic_exchange","route.queue.ii.org",msg);//匹配两个
//    }

    public void sendMegToTopicEXchange01(String msg){
        log.info("MQSender 发送了一条Topic01新消息：" + msg);
        rabbitTemplate.convertAndSend( "topicExchange","queue",msg);//发送方要根据路由规则发送给指定队列，匹配两个
    }


}
