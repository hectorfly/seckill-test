package com.xb.seckilltest.vo;

import com.xb.seckilltest.pojo.Goods;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class GoodsVO extends Goods {
    /**
     * 秒杀价
     */
    private BigDecimal seckillPrice;

    /**
     * 库存数量
     */
    private Integer stockCount;

    /**
     * 秒杀开始时间
     */
    private Date startDate;

    /**
     * 秒杀结束时间
     */
    private Date endDate;
}
