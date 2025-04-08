package com.xb.seckilltest.controller;


import com.xb.seckilltest.rabbitMQ.MQSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2025-03-30
 */
@Controller
@RequestMapping("/t-user")
public class UserController {

//    @Resource
//    MQSender mqSender;
//
//    @RequestMapping("/mq")
//    @ResponseBody
//    public void mqTest(){
//        mqSender.sendMeg("hello world");
//    }
//
//    @RequestMapping("/fanExMQ")
//    @ResponseBody
//    public void fanExMQTest(){
//        mqSender.sendMegToFanoutEXchange01("hello world");
//    }
//    @RequestMapping("/directExMQ01")
//    @ResponseBody
//    public void directExMQ01(){
//        mqSender.sendMegToDirectEXchange01("hello directExMQ01");
//    }
//    @RequestMapping("/directExMQ02")
//    @ResponseBody
//    public void directExMQ02(){
//        mqSender.sendMegToDirectEXchange02("hello directExMQ02");
//    }
//    @RequestMapping("/topicExMQ01")
//    @ResponseBody
//    public void topicExMQ01(){
//        mqSender.sendMegToTopicEXchange01("hello topicExMQ01");
//    }
//    @RequestMapping("/topicExMQ02")
//    @ResponseBody
//    public void topicExMQ02(){
//        mqSender.sendMegToTopicEXchange02("hello topicExMQ02");
//    }

}
