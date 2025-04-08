package com.xb.seckilltest.service;

import com.xb.seckilltest.pojo.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.seckilltest.vo.GoodsVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2025-03-31
 */
public interface IGoodsService extends IService<Goods> {

    List<GoodsVO> getAllGoodsVO();

    GoodsVO getGoodVOById(Long goodId);
}
