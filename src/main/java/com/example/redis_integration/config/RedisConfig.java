package com.example.redis_integration.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;
import java.time.Duration;
import java.util.Optional;


@Configuration
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private Integer port;

    @Bean
    public LettuceConnectionFactory createRedisConfig(RedisConfiguration redisConfiguration) {
        return new LettuceConnectionFactory(redisConfiguration);
    }

    @Bean
    public RedisStandaloneConfiguration defaultRedisConfig() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(host);
        config.setPort(port);
        return config;
    }

    @Bean
    @Primary
    public CacheManager shortTermCache(LettuceConnectionFactory redisConnectionFactory) {
        return createCacheManager(redisConnectionFactory, Optional.of(2 * 60 * 60));
    }

    @Bean
    @Primary
    public RedisTemplate<String,Serializable> createRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory){
        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(lettuceConnectionFactory);
        return template;
    }

    private CacheManager createCacheManager(LettuceConnectionFactory lettuceConnectionFactory, Optional<Integer> ttl) {
        return RedisCacheManager.builder(lettuceConnectionFactory).cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().entryTtl(ttl.isEmpty() ? Duration.ofMinutes(1L) : ttl.map(Duration::ofMillis).get())).build();
    }


}
