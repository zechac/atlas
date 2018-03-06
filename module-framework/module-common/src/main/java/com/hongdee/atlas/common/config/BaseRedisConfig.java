package com.hongdee.atlas.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by hua on 2016/10/14.
 */
@Configuration
public abstract class BaseRedisConfig {
    @Autowired
    private Environment environment;
    /*
    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory=new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(environment.getRequiredProperty("redis.addr"));
        jedisConnectionFactory.setPassword(environment.getRequiredProperty("redis.password"));
        jedisConnectionFactory.setTimeout(Integer.valueOf(environment.getRequiredProperty("redis.timeout")));
        jedisConnectionFactory.setDatabase(Integer.valueOf(environment.getProperty("redis.database","0")));
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate redisTemplate(JedisConnectionFactory jedisConnectionFactory){
        RedisTemplate redisTemplate=new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        return redisTemplate;
    }
    */
}
