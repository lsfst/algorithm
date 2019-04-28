package com.algorithm.longpoll.deferredResult;

public class PoolingMessage {

    private String vid;
    private long lastConnectime;
    private String message;

    public PoolingMessage( String vid){
        this(vid,"");
    }

    public PoolingMessage( String vid, String message){
        this.vid = vid;
        this.lastConnectime = System.currentTimeMillis();
        this.message = message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }
}
