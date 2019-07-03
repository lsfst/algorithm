package com.algorithm.concurrent.memorization;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @program algorithm
 * @description:采用putIfAbsent操作保证原子性
 * 不足之处是没有解决缓存逾期和缓存清理问题
 * @author: liangshaofeng
 * @create: 2019/06/28 23:24
 */
public class Memoizer4<A,V> implements Computable<A,V> {
    private final Map<A,Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A,V> c;

    public Memoizer4(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        while (true){
            Future<V> f = cache.get(arg);
            if(f == null){
                Callable<V> eval = new Callable<V>() {
                    @Override
                    public V call() throws Exception {
                        return c.compute(arg);
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
}
