package com.algorithm.concurrent.memorization;

import java.util.HashMap;
import java.util.Map;

/**
 * @program algorithm
 * @description: HashMap作为缓存，闭锁保证安全
 * @author: liangshaofeng
 * @create: 2019/06/28 22:57
 */
public class Memoizer<A,V> implements Computable<A,V> {

    private final Map<A,V> cache = new HashMap<>();
    private final Computable<A,V> c;

    public Memoizer(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public synchronized V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if(result==null){
            result = c.compute(arg);
            cache.put(arg,result);
        }
        return result;
    }
}
