package com.xb.seckilltest.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RespBeanEnum {
    SUCCESS(200,"SUCCESS"),
    ERROR(500,"ERROR"),
    LOGIN_VERIFY_ERROR(500001,"信息校验失败，请认真填写"),
    LOGIN_MESSAGE_ERROR(500002,"用户名或密码错误"),
    BIND_ERROR(500003,"数据校验失败，请认真填写"),
    USER_MESSAGE_VERIFY_ERROR(500000,"用户数据异常，请重新登录"),
    SECKILL_ORDER_ONLY_ONE(500004,"只能抢购一个商品"),
    SECKILL_GOODS_STOCK_NULL(500005,"库存不足"),
    SECKILL_GOODS_BUY_FAILED(500006,"抢购失败"),
    VERIFY_FAILED(500008,"验证码错误"),
    ACCESS_FAILED(500009,"操作频繁，请稍后再试"),
    SECKILL_GOODS_NULL_FAILED(500007,"商品查询不到");

    private final Integer code;
    private final String message;
}
