package com.algorithm.concurrent.monitor;

/**
 * @program algorithm
 * @description: 坐标类
 * @author: liangshaofeng
 * @create: 2019/06/27 15:56
 */
public class MutablePoint {
    public int x,y;

    public MutablePoint() {
        x = 0;
        y = 0;
    }

    public MutablePoint(MutablePoint p){
        this.x = p.x;
        this.y = p.y;
    }
}
