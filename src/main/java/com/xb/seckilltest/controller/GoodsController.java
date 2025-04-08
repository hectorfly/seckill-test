package com.xb.seckilltest.controller;

import com.xb.seckilltest.constants.GoodsStatusConstants;
import com.xb.seckilltest.constants.HttpConstants;
import com.xb.seckilltest.exception.GlobalException;
import com.xb.seckilltest.pojo.User;
import com.xb.seckilltest.service.IGoodsService;
import com.xb.seckilltest.vo.DetailVO;
import com.xb.seckilltest.vo.GoodsVO;
import com.xb.seckilltest.vo.RespBean;
import com.xb.seckilltest.vo.RespBeanEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Resource
    IGoodsService goodsService;

    @Resource
    ThymeleafViewResolver thymeleafViewResolver;

    /**
     * 压测结果：优化前
     * Windows：1000线程循环10次访问   同一用户    QPS ：2350
     *          10000 10   不同用户  qps:1462
     *                             添加缓存qps: 1：4033  2：5284
     * Linux：1000线程循环10次访问   同一用户    QPS ：453
     *
     *
     *
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value = "toList" , produces = "text/html;charset=utf-8")//todo
    @ResponseBody
    public String toList(Model model, User user, HttpServletRequest request, HttpServletResponse response) {
//        //不使用redis   //不使用解析器
////        Object obj = session.getAttribute(ticket);
//
//        Object obj = redisTemplate.opsForValue().get(HttpConstants.USER_REDIS + ticket);
//        if(obj == null){
//            throw new GlobalException(RespBeanEnum.USER_MESSAGE_VERIFY_ERROR);
//        }
//        User user = (User)obj;

        //使用redis做页面缓存,存在缓存则使用，不存在则创建
        String html = (String)redisTemplate.opsForValue().get(HttpConstants.HTML_REDIS + HttpConstants.USER_REDIS + user.toString() + ":" + "toList");
        if(!StringUtils.isEmpty(html)){
            return html;
        }


        List<GoodsVO> allGoodsVO = goodsService.getAllGoodsVO();
        model.addAttribute("user", user);
        model.addAttribute("goodsList", allGoodsVO);
        //使用thymeleaf创建缓存

        WebContext webContext = new WebContext(request,response, request.getServletContext(),request.getLocale(),model.asMap());

        html = thymeleafViewResolver.getTemplateEngine().process("goodsList", webContext);

        redisTemplate.opsForValue().set(HttpConstants.HTML_REDIS + HttpConstants.USER_REDIS + user.toString() + ":" + "toList",html);

        return html;
    }
//不使用静态缓存
//    @RequestMapping(value = "/detail/{goodId}", produces = "text/html;charset=utf-8")
//    @ResponseBody
//    public String detail(Model model, User user, @PathVariable("goodId") Long goodId, HttpServletRequest request, HttpServletResponse response) {
//        //使用redis做页面缓存,存在缓存则使用，不存在则创建
//        String html = (String)redisTemplate.opsForValue().get(HttpConstants.HTML_REDIS + HttpConstants.USER_REDIS + user.toString() + ":"  + "detail:" + goodId);
//        if(!StringUtils.isEmpty(html)){
//            return html;
//        }
//
//        GoodsVO good = goodsService.getGoodVOById(goodId);
//
//        Date now = new Date();
//        Date startDate = good.getStartDate();
//        Date endDate = good.getEndDate();
//
//        int secKillStatus = GoodsStatusConstants.GOOD_SECKILL_STATUS_NOT_READY;
//        Long remainSeconds = 0L;//默认开始秒杀
//        if (now.after(startDate) && now.before(endDate)) {
//            secKillStatus = GoodsStatusConstants.GOOD_SECKILL_STATUS_READY;
//        } else if (now.after(endDate)) {
//            secKillStatus = GoodsStatusConstants.GOOD_SECKILL_STATUS_END;
//            remainSeconds = -1L;//过期
//        }else {
//            //当还没开始秒杀时才计算剩余时间
//            remainSeconds = (startDate .getTime() - now.getTime())/1000;
//        }
//
//
//        model.addAttribute("user", user);
//        model.addAttribute("goods", good);
//        model.addAttribute("secKillStatus", secKillStatus);
//        model.addAttribute("remainSeconds", remainSeconds);
//
//
//        //使用thymeleaf创建缓存
//
//        WebContext webContext = new WebContext(request,response, request.getServletContext(),request.getLocale(),model.asMap());
//
//        html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail", webContext);
//
//        redisTemplate.opsForValue().set(HttpConstants.HTML_REDIS + HttpConstants.USER_REDIS + user.toString() + ":"  + "detail:" + goodId,html);
//
//        return html;
//    }


    @RequestMapping("/detail/{goodId}")
    @ResponseBody
    public RespBean detail(User user, @PathVariable("goodId") Long goodId) {
        GoodsVO good = goodsService.getGoodVOById(goodId);

        Date now = new Date();
        Date startDate = good.getStartDate();
        Date endDate = good.getEndDate();

        int secKillStatus = GoodsStatusConstants.GOOD_SECKILL_STATUS_NOT_READY;
        Long remainSeconds = 0L;//默认开始秒杀
        if (now.after(startDate) && now.before(endDate)) {
            secKillStatus = GoodsStatusConstants.GOOD_SECKILL_STATUS_READY;
        } else if (now.after(endDate)) {
            secKillStatus = GoodsStatusConstants.GOOD_SECKILL_STATUS_END;
            remainSeconds = -1L;//过期
        }else {
            //当还没开始秒杀时才计算剩余时间
            remainSeconds = (startDate .getTime() - now.getTime())/1000;
        }

        DetailVO detailVO = new DetailVO();
        detailVO.setGoodsVo(good);
        detailVO.setRemainSeconds(remainSeconds);
        detailVO.setUser(user);
        detailVO.setSecKillStatus(secKillStatus);

        return RespBean.success(detailVO);
    }


}
