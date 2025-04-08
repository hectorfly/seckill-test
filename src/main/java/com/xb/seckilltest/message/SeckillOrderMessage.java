package com.xb.seckilltest.message;

import com.xb.seckilltest.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillOrderMessage {
    private User user;
    private Long goodsId;

}
