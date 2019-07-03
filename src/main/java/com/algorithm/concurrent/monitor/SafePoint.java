package com.algorithm.concurrent.monitor;

/**
 * @program algorithm
 * @description:
 * @author: liangshaofeng
 * @create: 2019/06/27 16:24
 */
public class SafePoint {
    private int x,y;

    public SafePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private SafePoint(int a[]){
        this(a[0],a[1]);
    }

    public SafePoint(SafePoint safePoint){
        this(safePoint.get());
    }

    public synchronized int[] get(){
        return new int[]{x,y};
    }

    public synchronized void set(int x,int y){
        this.x = x;
        this.y = y;
    }
}
