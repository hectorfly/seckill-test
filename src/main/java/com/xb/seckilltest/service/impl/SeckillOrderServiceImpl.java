package com.xb.seckilltest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xb.seckilltest.exception.GlobalException;
import com.xb.seckilltest.mapper.OrderMapper;
import com.xb.seckilltest.pojo.Order;
import com.xb.seckilltest.pojo.SeckillGoods;
import com.xb.seckilltest.pojo.SeckillOrder;
import com.xb.seckilltest.mapper.SeckillOrderMapper;
import com.xb.seckilltest.pojo.User;
import com.xb.seckilltest.service.IOrderService;
import com.xb.seckilltest.service.ISeckillGoodsService;
import com.xb.seckilltest.service.ISeckillOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.seckilltest.vo.GoodsVO;
import com.xb.seckilltest.vo.RespBeanEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2025-03-31
 */
@Service
@Transactional
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements ISeckillOrderService {

    @Resource
    IOrderService orderService;

    @Resource
    OrderMapper orderMapper;

    @Resource
    SeckillOrderMapper seckillOrderMapper;

    @Resource
    ISeckillGoodsService seckillGoodsService;

    @Override
    public Order doSeckill(User user, GoodsVO goods) {

        //秒杀库存减一
        SeckillGoods seckillgoods = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>().eq("goods_id", goods.getId()));
        //没做判断导致可能的超卖
//        seckillgoods.setStockCount(goods.getStockCount() - 1);
//        seckillGoodsService.updateById(seckillgoods);
        //做判断防止超卖，同时在数据库设置索引，防止一个用户购买相同的商品
        boolean update = seckillGoodsService.update(new UpdateWrapper<SeckillGoods>().setSql("stock_count= stock_count -1").gt("stock_count",0).eq("goods_id",goods.getId()));

        if(!update){
            throw new GlobalException(RespBeanEnum.SECKILL_GOODS_BUY_FAILED);
        }
        //创建订单
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goods.getId());
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(goods.getGoodsPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        orderMapper.insert(order);
        //创建秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(goods.getId());
        seckillOrderMapper.insert(seckillOrder);

        return order;
    }
}
