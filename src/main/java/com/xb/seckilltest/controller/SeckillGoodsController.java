package com.xb.seckilltest.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wf.captcha.ArithmeticCaptcha;
import com.xb.seckilltest.annotation.AccessLimit;
import com.xb.seckilltest.constants.HttpConstants;
import com.xb.seckilltest.exception.GlobalException;
import com.xb.seckilltest.message.SeckillOrderMessage;
import com.xb.seckilltest.pojo.Order;
import com.xb.seckilltest.pojo.SeckillGoods;
import com.xb.seckilltest.pojo.SeckillOrder;
import com.xb.seckilltest.pojo.User;
import com.xb.seckilltest.rabbitMQ.MQSender;
import com.xb.seckilltest.service.IGoodsService;
import com.xb.seckilltest.service.IOrderService;
import com.xb.seckilltest.service.ISeckillOrderService;
import com.xb.seckilltest.service.impl.GoodsServiceImpl;
import com.xb.seckilltest.utils.JsonUtil;
import com.xb.seckilltest.utils.MD5Util;
import com.xb.seckilltest.vo.GoodsVO;
import com.xb.seckilltest.vo.RespBean;
import com.xb.seckilltest.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2025-03-31
 */
@Controller
@RequestMapping("/seckill")
@DependsOn("redisTemplate")
@Slf4j
public class SeckillGoodsController implements InitializingBean {

    @Resource
    RedisTemplate<String,Object> redisTemplate;

    @Resource
    ISeckillOrderService seckillOrderService;

    @Resource
    IOrderService orderService;

    @Resource
    IGoodsService goodsService;

    @Resource
    MQSender mqSender;


    @Resource
    DefaultRedisScript<Long> stockJudgeScript;

    private Map<Long, Boolean> EmptyStockMap = new HashMap<>();

//    /**
//     *
//     * windows :  10000 * 10 前 QPS:354    1000*10  qian  225
//     *
//     *
//     * @param user
//     * @param goodsId
//     * @param model
//     * @return
//     */
//    @RequestMapping("/doSeckill")
//    public String doSeckill(User user, Long goodsId, Model model){
//        //查找订单中是否已经有该用户关联商品的订单（买过没有）
//        SeckillOrder one = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().eq("user_id",user.getId()).eq("goods_id",goodsId));
//
//        if(one != null){
//            //当存在订单信息就返回错误界面
//            model.addAttribute("errmsg", RespBeanEnum.SECKILL_ORDER_ONLY_ONE.getMessage());
//            return "secKillFail";
//        }
//
//        //查看库存
//        GoodsVO good = goodsService.getGoodVOById(goodsId);
//        if(!(good.getStockCount() > 0)){
//            //当库存不足就返回错误界面
//            model.addAttribute("errmsg", RespBeanEnum.SECKILL_GOODS_STOCK_NULL.getMessage());
//            return "secKillFail";
//        }
//        //库存充足则-1且创建商品订单与秒杀订单并返回订单信息
//
//        Order order = seckillOrderService.doSeckill(user,good);
//
//
//
//        model.addAttribute("goods",good);
//        model.addAttribute("order",order);
//
//        return "orderDetail";
//    }

//    /**
//     *
//     * windows :  10000 * 10 前 QPS:354    1000*10  qian  225
//     *
//     *
//     * @param user
//     * @param goodsId
//     * @return
//     */
//    @RequestMapping("/doSeckill")
//    @ResponseBody
//    public RespBean doSeckill(User user, Long goodsId){
//        //查找订单中是否已经有该用户关联商品的订单（买过没有）    防止多买的措施1
//        Order one = (Order)redisTemplate.opsForValue().get(HttpConstants.ORDER_REDIS + user.getId() + ":" + goodsId);
//
//        if(one != null){
//            //当存在订单信息就返回错误界面
//            return RespBean.error(RespBeanEnum.SECKILL_ORDER_ONLY_ONE);
//        }
//
//        //查看库存
//        GoodsVO good = goodsService.getGoodVOById(goodsId);
//        if(!(good.getStockCount() > 0)){
//            //当库存不足就返回错误界面
//            return RespBean.error(RespBeanEnum.SECKILL_GOODS_STOCK_NULL);
//        }
//        //库存充足则-1且创建商品订单与秒杀订单并返回订单信息
//
//        Order order = seckillOrderService.doSeckill(user,good);
//
//        if(order==null){
//            return RespBean.error(RespBeanEnum.SECKILL_GOODS_BUY_FAILED);
//        }
//        redisTemplate.opsForValue().set(HttpConstants.ORDER_REDIS + user.getId() + ":" + goodsId,order);
//        return RespBean.success(order);
//    }

//    /**
//     *
//     * windows :  10000 * 10 前 QPS:354    1000*10  qian  225
//     *
//     *
//     * @param user
//     * @param goodsId
//     * @return
//     */
//    @RequestMapping("/doSeckill")
//    @ResponseBody
//    public RespBean doSeckill(User user, Long goodsId){
//        //查找订单中是否已经有该用户关联商品的订单（买过没有）    防止多买的措施1
//        Order one = (Order)redisTemplate.opsForValue().get(HttpConstants.ORDER_REDIS + user.getId() + ":" + goodsId);
//
//        if(one != null){
//            //当存在订单信息就返回错误界面
//            return RespBean.error(RespBeanEnum.SECKILL_ORDER_ONLY_ONE);
//        }
//
//        //使用缓存减少redis访问量
//        if(EmptyStockMap.get(goodsId)){
//            return RespBean.error(RespBeanEnum.SECKILL_GOODS_STOCK_NULL);
//        }
//        //查看库存
//        int good = (int)redisTemplate.opsForValue().get(HttpConstants.PRELOAD_GOODS_REDIS + goodsId);
//
//        if(good <=0 ){
//            RespBean.error(RespBeanEnum.SECKILL_GOODS_STOCK_NULL);
//        }
//        //为什么不判断上面的，应为有可能多个线程获取到然后导致多减
//        Long decrement = redisTemplate.opsForValue().decrement(HttpConstants.PRELOAD_GOODS_REDIS + goodsId);
//        if(decrement < 0){
//            //当库存不足就返回错误界面
//            EmptyStockMap.put(goodsId,true);
//            return RespBean.error(RespBeanEnum.SECKILL_GOODS_STOCK_NULL);
//        }
//        //库存充足则-1且创建商品订单与秒杀订单并返回订单信息
//
//        SeckillOrderMessage msg = new SeckillOrderMessage();
//        msg.setGoodsId(goodsId);
//        msg.setUser(user);
//        mqSender.sendMegToTopicEXchange01(JsonUtil.object2JsonStr(msg));
//
//        return RespBean.success(0);
//    }

//    /**
//     *
//     * windows :  10000 * 10 前 QPS:354    1000*10  qian  225
//     *
//     *
//     * @param user
//     * @param goodsId
//     * @return
//     */
//    @RequestMapping("/doSeckill")
//    @ResponseBody
//    public RespBean doSeckill(User user, Long goodsId){
//        //查找订单中是否已经有该用户关联商品的订单（买过没有）    防止多买的措施1
//        Order one = (Order)redisTemplate.opsForValue().get(HttpConstants.ORDER_REDIS + user.getId() + ":" + goodsId);
//
//        if(one != null){
//            //当存在订单信息就返回错误界面
//            return RespBean.error(RespBeanEnum.SECKILL_ORDER_ONLY_ONE);
//        }
//
//        //使用缓存减少redis访问量
//        if(EmptyStockMap.get(goodsId)){
//            return RespBean.error(RespBeanEnum.SECKILL_GOODS_STOCK_NULL);
//        }
//        //查看库存-》》》修改为使用lua脚本执行判断递减操作，
////        int good = (int)redisTemplate.opsForValue().get(HttpConstants.PRELOAD_GOODS_REDIS + goodsId);
////
////        if(good <=0 ){
////            RespBean.error(RespBeanEnum.SECKILL_GOODS_STOCK_NULL);
////        }
////        //为什么不判断上面的，应为有可能多个线程获取到然后导致多减
////        Long decrement = redisTemplate.opsForValue().decrement(HttpConstants.PRELOAD_GOODS_REDIS + goodsId);
//        Long decrement = redisTemplate.execute(stockJudgeScript, Collections.singletonList(HttpConstants.PRELOAD_GOODS_REDIS + goodsId));
//
//        if(decrement < 0){
//            //当库存不足就返回错误界面
//            EmptyStockMap.put(goodsId,true);
//            return RespBean.error(RespBeanEnum.SECKILL_GOODS_STOCK_NULL);
//        }
//        //库存充足则-1且创建商品订单与秒杀订单并返回订单信息
//
//        SeckillOrderMessage msg = new SeckillOrderMessage();
//        msg.setGoodsId(goodsId);
//        msg.setUser(user);
//        mqSender.sendMegToTopicEXchange01(JsonUtil.object2JsonStr(msg));
//
//        return RespBean.success(0);
//    }

    /**
     *
     * windows :  10000 * 10 前 QPS:354    1000*10  qian  225
     *
     *
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping("/{path}/doSeckill")
    @ResponseBody
    public RespBean doSeckill(User user, Long goodsId, @PathVariable String path){
        //对路径判断
        if(user == null | goodsId <= 0 | StringUtils.isEmpty(path)){
            return RespBean.error(RespBeanEnum.USER_MESSAGE_VERIFY_ERROR);
        }
        String tempPath = MD5Util.md5(user.getId().toString() + goodsId);
        if(!path.equals(tempPath)){
            return RespBean.error(RespBeanEnum.USER_MESSAGE_VERIFY_ERROR);
        }

        //查找订单中是否已经有该用户关联商品的订单（买过没有）    防止多买的措施1
        Order one = (Order)redisTemplate.opsForValue().get(HttpConstants.ORDER_REDIS + user.getId() + ":" + goodsId);

        if(one != null){
            //当存在订单信息就返回错误界面
            return RespBean.error(RespBeanEnum.SECKILL_ORDER_ONLY_ONE);
        }

        //使用缓存减少redis访问量
        if(EmptyStockMap.get(goodsId)){
            return RespBean.error(RespBeanEnum.SECKILL_GOODS_STOCK_NULL);
        }
        //查看库存-》》》修改为使用lua脚本执行判断递减操作，
//        int good = (int)redisTemplate.opsForValue().get(HttpConstants.PRELOAD_GOODS_REDIS + goodsId);
//
//        if(good <=0 ){
//            RespBean.error(RespBeanEnum.SECKILL_GOODS_STOCK_NULL);
//        }
//        //为什么不判断上面的，应为有可能多个线程获取到然后导致多减
//        Long decrement = redisTemplate.opsForValue().decrement(HttpConstants.PRELOAD_GOODS_REDIS + goodsId);
        Long decrement = redisTemplate.execute(stockJudgeScript, Collections.singletonList(HttpConstants.PRELOAD_GOODS_REDIS + goodsId));

        if(decrement < 0){
            //当库存不足就返回错误界面
            EmptyStockMap.put(goodsId,true);
            return RespBean.error(RespBeanEnum.SECKILL_GOODS_STOCK_NULL);
        }
        //库存充足则-1且创建商品订单与秒杀订单并返回订单信息

        SeckillOrderMessage msg = new SeckillOrderMessage();
        msg.setGoodsId(goodsId);
        msg.setUser(user);
        mqSender.sendMegToTopicEXchange01(JsonUtil.object2JsonStr(msg));

        return RespBean.success(0);
    }

    @RequestMapping("/path")
    @ResponseBody
    @AccessLimit(maxCount = 5,limitTime = 10,limitTimeType = TimeUnit.SECONDS)
    public RespBean getPath(User user,Long goodsId,String captcha){
        if(user == null){
            return RespBean.error(RespBeanEnum.USER_MESSAGE_VERIFY_ERROR);
        }
        //验证码
        String verifyCode = (String)redisTemplate.opsForValue().get(HttpConstants.CAPTCHA_REDIS + user.getId());
        if(StringUtils.isEmpty(verifyCode) | !captcha.equals(verifyCode)){
            return RespBean.error(RespBeanEnum.VERIFY_FAILED);
        }
        String path = MD5Util.md5(user.getId().toString() + goodsId);

        return RespBean.success(path);
    }

    @RequestMapping(value = "/captcha" , method = RequestMethod.GET)
    public void verifyCode(User user, Long goodsId, HttpServletResponse response){
        if(user == null | goodsId <= 0){
            throw  new GlobalException(RespBeanEnum.USER_MESSAGE_VERIFY_ERROR);
        }
        response.setContentType("image/jpg");
        response.setHeader("Pargam","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires",0);
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 32,3);
        redisTemplate.opsForValue().set(HttpConstants.CAPTCHA_REDIS + user.getId(),captcha.text());
        try {
            captcha.out(response.getOutputStream());
        } catch (IOException e) {
            log.info("验证码失败："+e.getMessage());
        }
    }

    /**
     * orderID：成功  0：排队中  -1：秒杀失败
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping("/getResult")
    @ResponseBody
    public RespBean getResult(User user,Long goodsId){
        Order order  = (Order) redisTemplate.opsForValue().get(HttpConstants.ORDER_REDIS + user.getId() + ":" + goodsId);
        if(order != null){
            return RespBean.success(order.getId());
        }else if(EmptyStockMap.get(goodsId)){//当缓存显示库存空了就失败
            return RespBean.success(-1);
        }
        return RespBean.success(0);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        //在初始化时就加载商品数量到redis，只是方便操作，业务中不这么做
        //添加缓存记录商品购买信息，减少redis的访问次数
        List<GoodsVO> allGoodsVO = goodsService.getAllGoodsVO();
        if( allGoodsVO == null | allGoodsVO.size() == 0){
            return;
        }
        for (GoodsVO goodsVO : allGoodsVO) {
            redisTemplate.opsForValue().set(HttpConstants.PRELOAD_GOODS_REDIS + goodsVO.getId(),goodsVO.getStockCount());
            if(goodsVO.getStockCount() <=0 ){
                EmptyStockMap.put(goodsVO.getId(),true);
            }else {
                EmptyStockMap.put(goodsVO.getId(),false);
            }
        }
    }
}
