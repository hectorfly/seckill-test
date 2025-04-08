package com.xb.seckilltest.controller;

import com.xb.seckilltest.service.IUserService;
import com.xb.seckilltest.vo.LoginVO;
import com.xb.seckilltest.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Resource
    IUserService userService;

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login.html";
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public RespBean doLogin(@Valid LoginVO loginVO, HttpServletRequest request, HttpServletResponse response) {//添加@Valid表示需要校验

        return userService.Login(loginVO,request,response);
    }
}
