package com.xb.seckilltest.vo;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {
    private long code;
    private String message;
    private Object obj;



    public static RespBean success(Object obj){
        return new RespBean(RespBeanEnum.SUCCESS.getCode(),RespBeanEnum.SUCCESS.getMessage(),obj);
    }

    public static RespBean success(){
        return success(null);
    }


    public static RespBean error(RespBeanEnum respBeanEnum,Object obj){
        if(respBeanEnum == null){
            return error(obj);
        }
        return new RespBean(respBeanEnum.getCode(),respBeanEnum.getMessage(), obj);
    }

    public static RespBean error(RespBeanEnum respBeanEnum){
        return error(respBeanEnum,null);
    }

    public static RespBean error(Object obj){
        return new RespBean(RespBeanEnum.ERROR.getCode(),RespBeanEnum.ERROR.getMessage(), obj);
    }


    public static RespBean error(){
        return error(null);
    }

}
