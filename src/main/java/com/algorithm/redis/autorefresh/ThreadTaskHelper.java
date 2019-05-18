package com.algorithm.redis.autorefresh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 缓存刷新任务异步执行
 */
public class ThreadTaskHelper {
    private static ExecutorService executorService= Executors.newFixedThreadPool(5);

    //记录正在执行 的刷新任务，防止多次刷新
    private static Map RUNNING_REFRESH_CACHE= new ConcurrentHashMap();

    public static Map<String,String> getRunningRefreshCache(){
        return RUNNING_REFRESH_CACHE;
    }

    public static void putRefreshCacheTask(String cacheKey){
        if(!hasRunningRefreshCacheTask(cacheKey)){
            RUNNING_REFRESH_CACHE.put(cacheKey,cacheKey);
        }
    }

    public static void removeRefreshCacheTask(String cacheKey){
        if(hasRunningRefreshCacheTask(cacheKey)){
            RUNNING_REFRESH_CACHE.remove(cacheKey);
        }
    }

    public static boolean hasRunningRefreshCacheTask(String cacheKey){
        return RUNNING_REFRESH_CACHE.containsKey(cacheKey);
    }

    public static void run(Runnable runnable){
        executorService.execute(runnable);
    }
}