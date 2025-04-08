package com.xb.seckilltest.config;

import com.xb.seckilltest.interceptor.AccessLimitInterceptor;
import com.xb.seckilltest.interceptor.LoginInterceptor;
import com.xb.seckilltest.resolvers.UserParamResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;


@Component
public class MvcConfig implements WebMvcConfigurer {

    @Resource
    LoginInterceptor loginInterceptor;
    @Resource
    AccessLimitInterceptor accessLimitInterceptor;

    @Resource
    UserParamResolver userParamResolver;

    // 构造器注入拦截器
//    public MvcConfig(LoginInterceptor loginInterceptor) {
//        this.loginInterceptor = loginInterceptor;
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login/**",
                        "/static/**",
                        // 显式添加实际请求路径
                        "/bootstrap/**",
                        "/jquery-validation/**",
                        "/js/**",
                        "/layer/**",
                        "/img/**",
                        "/layer/**",
                        // 通用资源类型排除
                        "**/*.js",
                        "**/*.css",
                        "**/*.html",
                        "/favicon.ico",
                        "/error");
        registry.addInterceptor(accessLimitInterceptor)
                .addPathPatterns("/**");

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userParamResolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://192.168.116.1") // 如 http://192.168.1.100:8080
//                .allowedMethods("*")
//                .allowedHeaders("*")
//                .allowCredentials(true); // 允许携带凭证（如Cookie）
//    }
}
