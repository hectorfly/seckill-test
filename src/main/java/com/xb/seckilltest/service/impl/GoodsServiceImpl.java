package com.xb.seckilltest.service.impl;

import com.xb.seckilltest.pojo.Goods;
import com.xb.seckilltest.mapper.GoodsMapper;
import com.xb.seckilltest.service.IGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.seckilltest.vo.GoodsVO;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2025-03-31
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Resource
    GoodsMapper goodsMapper;

    @Override
    public List<GoodsVO> getAllGoodsVO() {

        return goodsMapper.getAllGoodsVO();
    }

    @Override
    public GoodsVO getGoodVOById(Long goodId) {

        return goodsMapper.getGoodVOById(goodId);
    }
}
