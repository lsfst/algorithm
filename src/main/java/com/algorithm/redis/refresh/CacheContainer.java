package com.algorithm.redis.refresh;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @brief
 *    TODO 类功能作用及实现逻辑说明
 *
 * @see
 *
 */
public class CacheContainer {
    private static final String DEFAULT_CACHE_NAME="";

    private static final CacheItemConfig cacheItemConfig = new CacheItemConfig( DEFAULT_CACHE_NAME,40,25 );

    private static final Map<String,CacheItemConfig> CACHE_CONFIG_HOLDER=new ConcurrentHashMap(){
        {
            put(DEFAULT_CACHE_NAME,cacheItemConfig);
        }
    };

    public static void init( List<CacheItemConfig> cacheItemConfigs){
        if( cacheItemConfigs==null || cacheItemConfigs.isEmpty()){
            return;
        }
        cacheItemConfigs.forEach(cacheItemConfig -> {
            CACHE_CONFIG_HOLDER.put(cacheItemConfig.getName(),cacheItemConfig);
        });

    }

    public static void add( CacheItemConfig cacheItemConfig ) {
        if ( !CACHE_CONFIG_HOLDER.containsKey( cacheItemConfig.getName() ) ) {
            CACHE_CONFIG_HOLDER.put( cacheItemConfig.getName(), cacheItemConfig );
        }
    }

    public static boolean contains( String key) {
        return CACHE_CONFIG_HOLDER.containsKey( key );
    }

    public static CacheItemConfig getCacheItemConfigByCacheName(String cacheName){
        if(CACHE_CONFIG_HOLDER.containsKey(cacheName)) {
            return CACHE_CONFIG_HOLDER.get(cacheName);
        }
        CacheItemConfig config = new CacheItemConfig( CACHE_CONFIG_HOLDER.get(DEFAULT_CACHE_NAME) );
        config.setName( cacheName );
        return config;
    }

    public static List<CacheItemConfig> getCacheItemConfigs(){
        return CACHE_CONFIG_HOLDER
                .values()
                .stream()
                .filter(new Predicate<CacheItemConfig>() {
                    @Override
                    public boolean test(CacheItemConfig cacheItemConfig) {
                        return !cacheItemConfig.getName().equals(DEFAULT_CACHE_NAME);
                    }
                })
                .collect( Collectors.toList());
    }
}