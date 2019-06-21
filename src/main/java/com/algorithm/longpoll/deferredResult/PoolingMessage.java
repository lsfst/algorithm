package com.algorithm.longpoll.deferredResult;

import java.util.HashMap;
import java.util.Map;

public class PoolingMessage {

    private String vid;
    private long lastConnectime;
    private Map<String,String> message;

    public PoolingMessage( String vid){
        this(vid,"","");
    }

    public PoolingMessage( String vid, String type, String message){
        this.vid = vid;
        this.lastConnectime = System.currentTimeMillis();
        this.message = new HashMap<>();
        this.message.put( type,message );
    }

    public String getVid() {
        return vid;
    }

    public void setVid( String vid ) {
        this.vid = vid;
    }

    public long getLastConnectime() {
        return lastConnectime;
    }

    public void setLastConnectime( long lastConnectime ) {
        this.lastConnectime = lastConnectime;
    }

    public Map< String, String> getMessage() {
        return message;
    }

    public void setMessage( Map message ) {
        this.message = message;
    }

    public void setMessage( String type,String msg) {
        if(this.message==null){
            this.message = new HashMap<>(  );
        }
        this.message.put( type,msg );
    }

}
