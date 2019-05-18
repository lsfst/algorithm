package com.algorithm.redis.autorefresh;

import java.io.Serializable;

/**
 * @brief
 *    TODO 类功能作用及实现逻辑说明
 *
 * @see
 *
 */
public class CacheItemConfig implements Serializable {

    private static final long DEFAULT_EXPIRE = 36L;

    private static final long DEFAULT_PRELOAD = 25L;
    /**
     * 缓存容器名称
     */
    private String name;
    /**
     * 缓存失效时间
     */
    private long expiryTimeSecond;
    /**
     * 当缓存存活时间达到此值时，主动刷新缓存
     */
    private long preLoadTimeSecond;

    public CacheItemConfig(){

    }

    public CacheItemConfig( String name, long expiryTimeSecond, long preLoadTimeSecond ) {
        this.name = name;
        this.expiryTimeSecond = expiryTimeSecond;
        this.preLoadTimeSecond = preLoadTimeSecond;
    }

    public CacheItemConfig( String name ) {
        this.name = name;
        this.expiryTimeSecond = DEFAULT_EXPIRE;
        this.preLoadTimeSecond = DEFAULT_PRELOAD;
    }

    public CacheItemConfig( CacheItemConfig itemConfig ) {
        this.expiryTimeSecond = itemConfig.expiryTimeSecond;
        this.preLoadTimeSecond = itemConfig.preLoadTimeSecond;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getExpiryTimeSecond() {
        return expiryTimeSecond;
    }

    public void setExpiryTimeSecond(long expiryTimeSecond) {
        this.expiryTimeSecond = expiryTimeSecond;
    }

    public long getPreLoadTimeSecond() {
        return preLoadTimeSecond;
    }

    public void setPreLoadTimeSecond(long preLoadTimeSecond) {
        this.preLoadTimeSecond = preLoadTimeSecond;
    }
}