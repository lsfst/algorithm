package com.algorithm.redis.autorefresh;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  自定义的redis缓存管理器
 *  * 支持方法上配置过期时间
 *  * 支持热加载缓存：缓存即将过期时主动刷新缓存
 *
 */
public class CustomizedRedisCacheManager extends RedisCacheManager {

    private RedisCacheWriter redisCacheWriter;
    private static RedisCacheConfiguration defaultRedisCacheConfiguration;
    private RedisOperations redisOperations;
    private static Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer;


    static {
        jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility( PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping( ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
    }

    public CustomizedRedisCacheManager( RedisConnectionFactory connectionFactory, RedisOperations redisOperations, List<CacheItemConfig> cacheItemConfigList) {

        this(
                RedisCacheWriter.nonLockingRedisCacheWriter( connectionFactory ),
                RedisCacheConfiguration.defaultCacheConfig().entryTtl( Duration.ofSeconds( 30 ) )
//                        .serializeValuesWith(
//                                RedisSerializationContext
//                                        .SerializationPair
//                                        .fromSerializer( jackson2JsonRedisSerializer )
//                        )
                ,
                cacheItemConfigList
                        .stream()
                        .collect( Collectors.toMap(CacheItemConfig::getName, cacheItemConfig -> {
                            RedisCacheConfiguration cacheConfiguration =
                                    RedisCacheConfiguration
                                            .defaultCacheConfig()
                                            .serializeValuesWith(
                                                    RedisSerializationContext
                                                            .SerializationPair
                                                            .fromSerializer(jackson2JsonRedisSerializer)
                                            )
                                            .entryTtl(Duration.ofSeconds(cacheItemConfig.getExpiryTimeSecond()))
                                            .prefixKeysWith(cacheItemConfig.getName());
//                            new RedisCacheConfiguration(Duration.ofSeconds(cacheItemConfig.getExpiryTimeSecond()),true,true, CacheKeyPrefix.simple(), RedisSerializationContext.SerializationPair.fromSerializer( RedisSerializer.string()), RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer),new DefaultFormattingConversionService());
                            return cacheConfiguration;
                        }))
        );
        this.redisOperations=redisOperations;
        CacheContainer.init(cacheItemConfigList);

    }

    public CustomizedRedisCacheManager(
            RedisCacheWriter redisCacheWriter
            ,RedisCacheConfiguration redisCacheConfiguration,
            Map<String, RedisCacheConfiguration> redisCacheConfigurationMap) {
        super(redisCacheWriter,redisCacheConfiguration,redisCacheConfigurationMap);
        this.redisCacheWriter=redisCacheWriter;
//        this.defaultRedisCacheConfiguration=redisCacheConfiguration;
    }

    @Override
    public Cache getCache( String name) {
        //默认的RedisCache采用的JDK序列化，getCache()获取不到对应cache时会自动生成默认RedisCache，需要进行重写
//        Cache cache = super.getCache( name );
//        return cache;
        Cache cache = super.lookupCache(name);
        if(null!=cache){
            return cache;
        }
        CacheItemConfig itemConfig = CacheContainer.getCacheItemConfigByCacheName(name);
        this.defaultRedisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                .serializeValuesWith(
                        RedisSerializationContext
                                .SerializationPair
                                .fromSerializer(jackson2JsonRedisSerializer)
                )
                .entryTtl(Duration.ofSeconds(itemConfig.getExpiryTimeSecond()))
                //设置key的前缀，采用默认的就好
//                .prefixKeysWith(itemConfig.getName())
        ;

        CustomizedRedisCache redisCache= new CustomizedRedisCache(
                name,
                this.redisCacheWriter,
                this.defaultRedisCacheConfiguration,
                this.redisOperations
        );
        super.addCache( redisCache );
        return redisCache;
    }
}