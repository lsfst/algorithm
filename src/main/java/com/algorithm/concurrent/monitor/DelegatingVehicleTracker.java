package com.algorithm.concurrent.monitor;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program algorithm
 * @description:
 * @author: liangshaofeng
 * @create: 2019/06/27 16:08
 */
public class DelegatingVehicleTracker {

    private final ConcurrentHashMap<String,Point> localtions;

    private final Map<String,Point> unmodifiableMap;

    public DelegatingVehicleTracker(Map<String,Point> points) {
        this.localtions = new ConcurrentHashMap<>(points);
        this.unmodifiableMap = Collections.unmodifiableMap(points);
    }

    public Map<String,Point> getLocations(){
        return unmodifiableMap;
    }

    public Point getLocation(String id){
        return localtions.get(id);
    }

    public void setLocaltions(String id, int x,int y){
        if(localtions.replace(id,new Point(x,y)) == null){
            throw new IllegalArgumentException("invalid vehicle name :"+id);
        }
    }
}
