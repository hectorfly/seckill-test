package com.xb.seckilltest.exception;

import com.xb.seckilltest.vo.RespBean;
import com.xb.seckilltest.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public RespBean exceptionHandler(Exception e){
        log.info(e.toString());
        if(e instanceof GlobalException){
            GlobalException globalException = (GlobalException) e;
            return RespBean.error(globalException.getRespBeanEnum());
        }else if(e instanceof BindException){
            BindException bindException = (BindException) e;
            RespBean error = RespBean.error(RespBeanEnum.BIND_ERROR);
            error.setMessage("参数校验异常:" + bindException.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return error;
        }
        return  RespBean.error();
    }

}
