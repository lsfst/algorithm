package com.algorithm.redis.autorefresh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.util.MethodInvoker;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @brief
 *    TODO 类功能作用及实现逻辑说明
 *
 * @see
 *
 */
@Component
public class CacheSupportImpl implements CacheSupport,InvocationRegistry {

    /**
     * 记录容器与所有执行方法信息
     */
    private Map<String, Map<String,CachedInvocation>> cacheToInvocationsMap;

    @Autowired
    private CacheManager cacheManager;

    private void refreshCache(CachedInvocation invocation, String cacheName) {

        boolean invocationSuccess;
        Object computed = null;
        try {
            computed = invoke(invocation);
            invocationSuccess = true;
        } catch (Exception ex) {
            invocationSuccess = false;
        }
        if (invocationSuccess) {
            if (cacheToInvocationsMap.get(cacheName) != null) {
                cacheManager.getCache(cacheName).put(invocation.getKey(), computed);
            }
        }
    }

    private Object invoke(CachedInvocation invocation)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final MethodInvoker invoker = new MethodInvoker();
        invoker.setTargetObject(invocation.getTargetBean());
        invoker.setArguments(invocation.getArguments());
        invoker.setTargetMethod(invocation.getTargetMethod().getName());
        invoker.prepare();
        return invoker.invoke();
    }


    @PostConstruct
    public void initialize() {
        cacheToInvocationsMap = new ConcurrentHashMap<String, Map<String,CachedInvocation>>(cacheManager.getCacheNames().size());
            for (final String cacheName : cacheManager.getCacheNames()) {
                cacheToInvocationsMap.put(cacheName, new ConcurrentHashMap<>());
            }
    }

    @Override
    public void registerInvocation(Object targetBean, Method targetMethod, Object[] arguments, Set<String> annotatedCacheNames) {

        StringBuilder sb = new StringBuilder();
        for (Object obj : arguments) {
            sb.append(obj.toString());
        }

        Object key = sb.toString();

        final CachedInvocation invocation = new CachedInvocation(key, targetBean, targetMethod, arguments);
        for (final String cacheName : annotatedCacheNames) {
            String[] cacheParams=cacheName.split("#");
            String realCacheName = cacheParams[0];
            if(!cacheToInvocationsMap.containsKey(realCacheName)) {
//                this.initialize();
                cacheToInvocationsMap.put( realCacheName, new ConcurrentHashMap<>());
            }
            if(!cacheToInvocationsMap.get(realCacheName).containsKey( key )){
                cacheToInvocationsMap.get(realCacheName).put(key.toString(),invocation);
            }


            //cache的缓存与刷新时间以cacheName为主，也即value，第一个注册的value是有效的
            if ( !CacheContainer.contains( cacheName ) ) {
                if ( cacheParams.length == 3 ) {
                    CacheContainer.add( new CacheItemConfig( cacheParams[ 0 ], Long.valueOf( cacheParams[ 1 ] ), Long.valueOf( cacheParams[ 2 ] ) ) );
                } else {
                    CacheContainer.add( new CacheItemConfig( cacheParams[ 0 ] ) );
                }
            }

        }
    }

    @Override
    public void refreshCache(String cacheName) {
        this.refreshCacheByKey(cacheName,null);
    }

    @Override
    public void refreshCacheByKey(String cacheName, String cacheKey) {
        if (cacheToInvocationsMap.get(cacheName) != null) {
            for (String key : cacheToInvocationsMap.get(cacheName).keySet()) {
                if(!StringUtils.isEmpty(cacheKey)&& key.equals(cacheKey)) {
                    refreshCache(cacheToInvocationsMap.get(cacheName).get(key), cacheName);
                }
            }
        }
    }
}