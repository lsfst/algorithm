package com.algorithm.concurrent.stripeMap;

/**
 * @program algorithm
 * @description: 自定义分段锁实现hash表
 * @author: liangshaofeng
 * @create: 2019/07/02 17:32
 */
public class StripedMap {
    private static final int N_LOCKS =16;
    private final Node[] buckets;
    private final Object[] locks;

    public StripedMap(int numBuckets) {
        buckets = new Node[numBuckets];
        locks = new Object[N_LOCKS];
        for(int i=0;i<N_LOCKS;i++){
            locks[i] = new Object();
        }
    }

    private final int hash(Object key){
        return Math.abs(key.hashCode())%buckets.length;
    }

    public Object get(Object key){
        int hash = hash(key);
        synchronized (locks[hash%N_LOCKS]){
            for(Node m = buckets[hash];m!=null;m = m.next){
                if(m.key.equals(key)){
                    return m.value;
                }
            }
        }
        return null;
    }

    public void clear(){
        //并不是原子操作，但可以接受
        for(int i=0;i<buckets.length;i++){
            synchronized (locks[i%N_LOCKS]){
                buckets[i]=null;
            }
        }
    }

    //......

    class Node{
         Object value;
         Object key;
         Node next;
    }
}
