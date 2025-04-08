package com.xb.seckilltest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.seckilltest.constants.HttpConstants;
import com.xb.seckilltest.exception.GlobalException;
import com.xb.seckilltest.mapper.UserMapper;
import com.xb.seckilltest.pojo.User;
import com.xb.seckilltest.service.IUserService;
import com.xb.seckilltest.utils.CookieUtil;
import com.xb.seckilltest.utils.MD5Util;
import com.xb.seckilltest.utils.UUIDUtil;
import com.xb.seckilltest.vo.LoginVO;
import com.xb.seckilltest.vo.RespBean;
import com.xb.seckilltest.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2025-03-30
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    UserMapper userMapper;

    @Resource
    RedisTemplate<String,Object> redisTemplate;

    @Override
    public RespBean Login(LoginVO loginVO, HttpServletRequest request, HttpServletResponse response) {
        //数据校验
        String password = loginVO.getPassword();
        String mobile = loginVO.getMobile();
//        if(StringUtils.isEmpty(password) || StringUtils.isEmpty(mobile)){
//            return RespBean.error(RespBeanEnum.LOGIN_VERIFY_ERROR);
//        }
//        if(!ValidatorUtil.mobileVerify(mobile)){
//            return RespBean.error(RespBeanEnum.LOGIN_VERIFY_ERROR);
//        }
        //对比数据库
        User user = userMapper.selectById(mobile);
        if(null == user){
//            return  RespBean.error(RespBeanEnum.LOGIN_MESSAGE_ERROR);
            throw new GlobalException(RespBeanEnum.LOGIN_MESSAGE_ERROR);
        }
        if(!user.getPassword().equals(MD5Util.fromPassToDBPass(password, user.getSalt()))){
//            return  RespBean.error(RespBeanEnum.LOGIN_MESSAGE_ERROR);
            throw new GlobalException(RespBeanEnum.LOGIN_MESSAGE_ERROR);
        }
        //添加cookie与session
//        //不是用redis的方式
        String uuid = UUIDUtil.uuid();
//        request.getSession().setAttribute(uuid,user);
        CookieUtil.setCookie(request,response, HttpConstants.USER_TICKET_COOKIE_NAME,uuid,3600,false);
        log.info("Login->>uuid:" + uuid);
        //使用redis存数据
        redisTemplate.opsForValue().set(HttpConstants.USER_REDIS + uuid,user);

        return RespBean.success(uuid);
    }

    @Override
    public User getUserByCookie(HttpServletRequest request, HttpServletResponse response) {

        String userTicket = CookieUtil.getCookieValue(request, HttpConstants.USER_TICKET_COOKIE_NAME);

        log.info("getUserByCookie->>userTicket:" + userTicket);

        //不使用redis
//        Object attribute = request.getSession().getAttribute(userTicket);
        Object attribute = redisTemplate.opsForValue().get(HttpConstants.USER_REDIS + userTicket);
        log.info("getUserByCookie->>user:" + attribute);
        if(attribute == null || userTicket == null){
            return null;
        }
        log.info("getUserByCookie->>resetCookie");
        //防止cookie失效
//        CookieUtil.setCookie(request, response, HttpConstants.USER_TICKET_COOKIE_NAME, userTicket);
        CookieUtil.setCookie(request,response, HttpConstants.USER_TICKET_COOKIE_NAME,userTicket,3600,false);
        return (User)attribute;
    }
}
