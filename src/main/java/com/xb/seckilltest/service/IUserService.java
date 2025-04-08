package com.xb.seckilltest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.seckilltest.pojo.User;
import com.xb.seckilltest.vo.LoginVO;
import com.xb.seckilltest.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2025-03-30
 */
public interface IUserService extends IService<User> {

    RespBean Login(LoginVO loginVO, HttpServletRequest request, HttpServletResponse response);

    User getUserByCookie(HttpServletRequest request, HttpServletResponse response);
}
