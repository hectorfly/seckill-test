package com.xb.seckilltest.service.impl;

import com.xb.seckilltest.pojo.Order;
import com.xb.seckilltest.mapper.OrderMapper;
import com.xb.seckilltest.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2025-03-31
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
