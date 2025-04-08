package com.xb.seckilltest.interceptor;

import com.xb.seckilltest.constants.HttpConstants;
import com.xb.seckilltest.constants.UserConText;
import com.xb.seckilltest.pojo.User;
import com.xb.seckilltest.service.IUserService;
import com.xb.seckilltest.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        User userByCookie = userService.getUserByCookie(request, response);

        String requestURI = request.getRequestURI();
        log.info("requestURI:" + requestURI);



        if(userByCookie == null){
            response.sendRedirect("/login/toLogin");
            return false;
        }
        //仅仅用作测试，逻辑不完全  UserConText
        UserConText.set(userByCookie);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
