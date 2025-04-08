package com.xb.seckilltest.interceptor;

import com.xb.seckilltest.annotation.AccessLimit;
import com.xb.seckilltest.constants.HttpConstants;
import com.xb.seckilltest.constants.UserConText;
import com.xb.seckilltest.pojo.User;
import com.xb.seckilltest.utils.JsonUtil;
import com.xb.seckilltest.vo.RespBean;
import com.xb.seckilltest.vo.RespBeanEnum;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class AccessLimitInterceptor implements HandlerInterceptor {

    @Resource
    RedisTemplate<String,Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod hm = (HandlerMethod) handler;
            User user = UserConText.get();
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if(accessLimit == null){
                return true;
            }
            Integer loginCount = (Integer) redisTemplate.opsForValue().get(HttpConstants.ACCESS_ANNOTATION_REDIS + user.getId());
            if(loginCount == null){
                redisTemplate.opsForValue().set(HttpConstants.ACCESS_ANNOTATION_REDIS + user.getId(),1,accessLimit.limitTime(),accessLimit.limitTimeType());
                return true;
            }
            if(loginCount >= accessLimit.limitTime()){
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                RespBean error = RespBean.error(RespBeanEnum.ACCESS_FAILED);
                out.write(JsonUtil.object2JsonStr(error));
                out.flush();
                out.close();
                return false;
            }
            redisTemplate.opsForValue().increment(HttpConstants.ACCESS_ANNOTATION_REDIS + user.getId());
        }
        return true;
    }
}
