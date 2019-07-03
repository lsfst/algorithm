package com.algorithm.concurrent.memorization;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program algorithm
 * @description:并发性提高，但无法避免计算较耗时情况下的重复计算
 * @author: liangshaofeng
 * @create: 2019/06/28 23:09
 */
public class Memoizer2<A,V> implements Computable<A,V> {
    private final Map<A,V> cache = new ConcurrentHashMap<>();
    private final Computable<A,V> c;

    public Memoizer2(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        V resullt = cache.get(arg);
        if(resullt==null){
            resullt = c.compute(arg);
            cache.put(arg,resullt);
        }
        return resullt;
    }
}
