package com.xb.seckilltest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xb.seckilltest.mapper")
public class SeckillTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillTestApplication.class, args);
    }

}
