package com.algorithm.concurrent.monitor;

import javax.annotation.concurrent.ThreadSafe;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @program algorithm
 * @description: 客户端加锁机制：需要注意同步操作的锁是否是同一个,也可以通过自身的内置锁进行同步（Combination）
 * @author: liangshaofeng
 * @create: 2019/06/27 16:36
 */
@ThreadSafe
public class ListHelper<E> {

    public List<E> unsafelist = Collections.synchronizedList(new ArrayList<>());
    public List<E> safelist = Collections.synchronizedList(new ArrayList<>());
    private final List<E> list;

    public ListHelper(List<E> list) {
        this.list = list;
    }

    public synchronized boolean putIfAbsentUnsafe(E x){
        boolean absent = !unsafelist.contains(x);
        if(absent){
            unsafelist.add(x);
        }
        return absent;
    }

    public boolean putIfAbsentSafe(E x){
        synchronized (safelist){
            boolean absent = !safelist.contains(x);
            if(absent){
                safelist.add(x);
            }
            return absent;
        }
    }


    public synchronized boolean putIfAbsent(E x){
        boolean absent = !list.contains(x);
        if(absent){
            list.add(x);
        }
        return absent;
    }

}
