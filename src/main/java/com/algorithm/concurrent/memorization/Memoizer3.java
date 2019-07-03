package com.algorithm.concurrent.memorization;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @program algorithm
 * @description:有所改善，但还是存在漏洞，f的空检查并非原子操作，存在同一个任务同时进行的概率
 * @author: liangshaofeng
 * @create: 2019/06/28 23:13
 */
public class Memoizer3<A,V> implements Computable<A,V> {

    private final Map<A,Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A,V> c;

    public Memoizer3(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        Future<V> f = cache.get(arg);
        if(f == null){
            Callable<V> eval = new Callable<V>() {
                @Override
                public V call() throws Exception {
                    return c.compute(arg);
                }
            };
            FutureTask<V> ft = new FutureTask<>(eval);
            f=ft;
            cache.put(arg,f);
            ft.run();
        }
        try {
            return f.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
