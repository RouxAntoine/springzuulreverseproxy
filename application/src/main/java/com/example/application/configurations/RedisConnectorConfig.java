package com.example.application.configurations;

import com.example.application.entities.SongEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConnectorConfig {

    @Bean
    ReactiveRedisTemplate<String, SongEntity> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<SongEntity> serializer = new Jackson2JsonRedisSerializer<>(SongEntity.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, SongEntity> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, SongEntity> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}