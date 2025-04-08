package com.xb.seckilltest.rabbitMQ;

import com.rabbitmq.client.Channel;
import com.xb.seckilltest.constants.HttpConstants;
import com.xb.seckilltest.exception.GlobalException;
import com.xb.seckilltest.mapper.GoodsMapper;
import com.xb.seckilltest.message.SeckillOrderMessage;
import com.xb.seckilltest.pojo.Order;
import com.xb.seckilltest.pojo.User;
import com.xb.seckilltest.service.ISeckillOrderService;
import com.xb.seckilltest.utils.JsonUtil;
import com.xb.seckilltest.vo.GoodsVO;
import com.xb.seckilltest.vo.RespBean;
import com.xb.seckilltest.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

@Service
@Slf4j
public class MQReceiver {

    @Resource
    ISeckillOrderService seckillOrderService;

    @Resource
    GoodsMapper goodsMapper;


    @Resource
    RedisTemplate<String,Object> redisTemplate;

    @RabbitListener(queues="queue")
    public void receiver(Object msg){
        log.info("receiver 接收到消息：" + msg);
    }

//    @RabbitListener(queues="queue01")
//    public void receiver01(Object msg){
//        log.info("queue01 接收到消息：" + msg);
//    }
//
//    @RabbitListener(queues="queue02")
//    public void receiver02(Object msg){
//        log.info("queue02 接收到消息：" + msg);
//    }

    @RabbitListener(queues="queue01")
    public void receiver01(String msg, Channel channel,@Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("queue01 接收到消息：" + msg);


        SeckillOrderMessage som = JsonUtil.jsonStr2Object(msg, SeckillOrderMessage.class);
        User user = som.getUser();
        Long goodsId = som.getGoodsId();
        //传到这里后再进行数据库的查询，以防万一
        GoodsVO goods = goodsMapper.getGoodVOById(goodsId);

        if(goods.getStockCount() <= 0 ){
            return;
        }

        Order order = null;
        try {
            order = seckillOrderService.doSeckill(user,goods);
        } catch (Exception e) {
            channel.basicNack(tag, false, false);
            throw new GlobalException();
        }

        if(order==null){
            return;
        }
        redisTemplate.opsForValue().set(HttpConstants.ORDER_REDIS + user.getId() + ":" + goodsId,order);
//        //更新redis库存
//
//        redisTemplate.delete(HttpConstants.PRELOAD_GOODS_REDIS + goodsId);
//        redisTemplate.opsForValue().set(HttpConstants.PRELOAD_GOODS_REDIS + goodsId,goods);

    }



}
