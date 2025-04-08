package com.xb.seckilltest.vo;

import com.xb.seckilltest.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品详情返回对象
 *
 * @author: LC
 * @date 2022/3/6 10:06 上午
 * @ClassName: DetailVo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailVO {


    private User user;

    private GoodsVO goodsVo;

    private int secKillStatus;

    private Long remainSeconds;


}
