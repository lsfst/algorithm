package com.algorithm.concurrent.threadfactory;



import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @program algorithm
 * @description:自定义线程基类，指定名称，异常处理，统计信息
 * @author: liangshaofeng
 * @create: 2019/07/02 13:16
 */
public class MyAppThread extends Thread {
    public static final String DEFAULT_NAME="MyAppThread";
    private static volatile boolean debugLifecycle = false;
    private static final AtomicInteger created = new AtomicInteger();
    private static final AtomicInteger alive = new AtomicInteger();
    private static final Logger logger = Logger.getAnonymousLogger();

     public MyAppThread(Runnable runnable,String name){
         super(runnable,name+"-"+created.incrementAndGet());
         setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){
             @Override
             public void uncaughtException(Thread t, Throwable e) {
                 logger.log(Level.SEVERE,"UNCAUGHT in thread "+t.getName(),e);
             }
         });
     }

    @Override
    public void run() {
        boolean debug = debugLifecycle;
        if(debug){
            logger.log(Level.FINE,"Created "+getName());
        }
        try {
            alive.incrementAndGet();
            super.run();
        }finally {
            alive.decrementAndGet();
            if(debug){
                logger.log(Level.FINE,"Existing "+getName());
            }
        }
    }

    public static int getThreadsCreated(){
         return created.get();
    }

    public static AtomicInteger getThreadAlive() {
        return alive;
    }

    public static boolean getDebug() {
        return debugLifecycle;
    }

    public static void setDebug(boolean debugLifecycle) {
        MyAppThread.debugLifecycle = debugLifecycle;
    }
}
