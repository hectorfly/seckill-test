package com.xb.seckilltest.mapper;

import com.xb.seckilltest.pojo.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xb.seckilltest.vo.GoodsVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2025-03-31
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    List<GoodsVO> getAllGoodsVO();

    GoodsVO getGoodVOById(Long goodId);
}
