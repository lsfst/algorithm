package com.algorithm.concurrent.monitor;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program algorithm
 * @description:
 * @author: liangshaofeng
 * @create: 2019/06/27 16:28;
 *
 */
public class PublishingVehicleTracker {

    private final Map<String,SafePoint> locations;

    private final Map<String,SafePoint> unmodifiableMap;

    public PublishingVehicleTracker(Map<String,SafePoint> map)  {
        this.locations = new ConcurrentHashMap<>(map);
        this.unmodifiableMap = Collections.unmodifiableMap(map);
    }

    public Map<String, SafePoint> getLocations() {
        return unmodifiableMap;
    }

    public SafePoint getLocation(String id){
        return locations.get(id);
    }

    public void setLocation(String id,int x,int y){
        if(!locations.containsKey(id)){
            throw new IllegalArgumentException("invalid vehicle name :"+id);
        }
        locations.get(id).set(x,y);
    }
}
