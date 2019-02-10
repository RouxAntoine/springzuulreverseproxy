package com.example.application.configurations;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
public class RedisConnectorConfig {

//    @Value("${com.example.application.redis.host}")
//    private String redisHost;
//
//    @Value("${com.example.application.redis.port}")
//    private String redisPort;

//    @Bean
//    public RedisConnectionFactory redisConnectionFactory(RedisProperties properties) {
//        // Lettuce
//        RedisConfiguration configuration = new RedisStandaloneConfiguration(properties);
//        return new LettuceConnectionFactory(configuration);
//    }
//

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public ReactiveRedisTemplate<String, String> redisTemplate(ReactiveRedisConnectionFactory factory) {
        return new ReactiveStringRedisTemplate(factory);
    }

//    /**
//     * get redis properties
//     * @return
//     */
//    public RedisProperties redisProperties() {
//        return new RedisProperties();
//    }
}
