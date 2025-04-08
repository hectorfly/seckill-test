package com.xb.seckilltest.service;

import com.xb.seckilltest.pojo.Order;
import com.xb.seckilltest.pojo.SeckillOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.seckilltest.pojo.User;
import com.xb.seckilltest.vo.GoodsVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2025-03-31
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {

    Order doSeckill(User user, GoodsVO goods);
}
