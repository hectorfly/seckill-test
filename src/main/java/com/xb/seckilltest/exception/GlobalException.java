package com.xb.seckilltest.exception;

import com.xb.seckilltest.vo.RespBean;
import com.xb.seckilltest.vo.RespBeanEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalException extends RuntimeException{

    private RespBeanEnum respBeanEnum;
}
