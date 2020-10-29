package com.example.demo.configs;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class RedisConfig {

  @Bean
  JedisConnectionFactory jedisConnectionFactory() {
    return new JedisConnectionFactory();
  }

  //GenericJackson2JsonRedisSerializer
  @Bean
  public RedisTemplate<String, String> redisTemplate() {
    RedisTemplate<String, String> template = new RedisTemplate<>();
    template.setDefaultSerializer(new StringRedisSerializer());
    template.setKeySerializer(new StringRedisSerializer());
    template.setHashKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new StringRedisSerializer());
    template.setConnectionFactory(jedisConnectionFactory());
    return template;
  }

  @Bean
  public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
    Map<String, RedisCacheConfiguration> cacheNamesConfigurationMap = new HashMap<>();
    cacheNamesConfigurationMap.put("cache20s", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(20)));
    cacheNamesConfigurationMap.put("cache30s", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(30)));
    cacheNamesConfigurationMap.put("cache40s", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(40)));

    return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory)
            .withInitialCacheConfigurations(cacheNamesConfigurationMap).build();
  }
}