package com.xb.seckilltest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class RedisConfig {

    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        //String的key序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //String的value序列化
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //hash的key序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //hash的value序列化
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        //注入连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        return redisTemplate;
    }

    @Bean
    public DefaultRedisScript<Long> stockJudgeScript(){
        DefaultRedisScript<Long> script =  new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("stock_judge.lua"));
        script.setResultType(Long.class);
        return script;
    }
}
