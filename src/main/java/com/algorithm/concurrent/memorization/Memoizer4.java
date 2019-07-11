package com.algorithm.concurrent.memorization;


import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program algorithm
 * @description:采用putIfAbsent操作保证原子性
 * @author: liangshaofeng
 * @create: 2019/06/28 23:24
 */
public class Memoizer4<A,V> implements Computable<A,V> {
    private final Map<A,Future<V>> cache = new ConcurrentHashMap<>();
    private final Map<A,Long> expires = new ConcurrentHashMap<>();
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final ReentrantLock lock  = new ReentrantLock();
    private final Computable<A,V> c;
    private final ServerCron serverCron = new ServerCron();

    public Memoizer4(Computable<A, V> c) {
        this.c = c;
        executor.scheduleWithFixedDelay(serverCron,1, 200, TimeUnit.MILLISECONDS);
    }

    @Override
    public V get(A arg) throws InterruptedException {
        expireIfNeed(arg);
        while (true){
            Future<V> f = cache.get(arg);
            if(f == null){
                Callable<V> eval = new Callable<V>() {
                    @Override
                    public V call() throws Exception {
                        return c.get(arg);
                    }
                };
                FutureTask<V> ft = new FutureTask<>(eval);
                f = cache.putIfAbsent(arg,ft);
                //f为null说明没有这个任务
                if(f == null){
                    f = ft;
                    ft.run();
                }
            }
            try {
                return f.get();
            } catch (CancellationException e) {
                //缓存的是future时，会导致缓存污染，若任务被取消，需要移除任务
                cache.remove(arg,f);
            }catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

    }

    public void set(A arg){
        expireIfNeed(arg);
        Future<V> f = cache.get(arg);
        if(f == null){
            cache.putIfAbsent(arg,new FutureTask<>(new Callable<V>() {
                @Override
                public V call() throws Exception {
                    return c.get(arg);
                }
            }));
        }
    }

    public void del(A arg){
        expireIfNeed(arg);
        cache.remove(arg);
    }


    public void expire(A arg ,int seconds){
        expireIfNeed(arg);
        if(cache.containsKey(expires)){
            long now = System.currentTimeMillis();
            expireAt(arg,now+seconds*1000);
        }
    }

    public void pexpire(A arg,int ms){
        expireIfNeed(arg);
        if(cache.containsKey(expires)) {
            long now = System.currentTimeMillis();
            pexpireAt(arg, now + ms);
        }
    }


    public void expireAt(A arg,long timestamp){
        expireIfNeed(arg);
        if(cache.containsKey(expires)) {
            pexpireAt(arg, timestamp * 1000);
        }
    }

    public void pexpireAt(A arg,long timestamp){
        expireIfNeed(arg);
        if(cache.containsKey(expires)) {
            expires.put(arg, timestamp);
        }
    }

    public boolean expired(A arg){
        expireIfNeed(arg);
        if(!expires.containsKey(arg)){
            return false;
        }
        return expires.get(arg)<System.currentTimeMillis();
    }

    public long ttl(A arg){
        expireIfNeed(arg);
        long ttl_ms = pttl(arg);
        if(ttl_ms>0){
            return ttl_ms/1000;
        }
        return ttl_ms;
    }

    public long pttl(A arg){
        expireIfNeed(arg);
        if(!cache.containsKey(arg)){
            return -2;
        }
        if(!expires.containsKey(arg)){
            return -1;
        }
        return expires.get(arg) - System.currentTimeMillis();
    }


    public void expireIfNeed(A arg){
        lock.lock();
        try {
            if(expired(arg)){
                cache.remove(arg);
                expires.remove(arg);
            }
        }finally {
            lock.unlock();
        }
    }

    private class ServerCron implements Runnable {

        @Override
        public void run() {
            for ( Map.Entry< A, Long> entry : expires.entrySet() ) {
                Long expireTime = entry.getValue();
                if (  expireTime > System.currentTimeMillis() ) {
                    del( entry.getKey() );
                }
            }
        }
    }


}
