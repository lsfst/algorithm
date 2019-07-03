package com.algorithm.concurrent.shutdown;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

/**
 * @program algorithm
 * @description:
 * @author: liangshaofeng
 * @create: 2019/06/29 16:37
 */
public class LogService {
    private final BlockingQueue<String> queue;
    private final LoggerThread loggerThread;
    private final PrintWriter writer;
    private boolean isShutdown;
    private int reservations;

    public LogService(BlockingQueue<String> queue, LoggerThread loggerThread, PrintWriter writer) {
        this.queue = queue;
        this.loggerThread = loggerThread;
        this.writer = writer;
    }

    public void start(){
        loggerThread.start();
    }

    public void stop(){
        synchronized (this){
            isShutdown = true;
        }
        loggerThread.interrupt();
    }

    public void log(String msg) throws InterruptedException {
        synchronized (this){
            if(isShutdown){
                throw new IllegalStateException();
            }
            ++reservations;
        }
        queue.put(msg);
    }



     class LoggerThread extends Thread {
        @Override
        public void run() {
            try {
                while (true){
                    try {
                        synchronized (LogService.this){
                            if(isShutdown && reservations==0){
                                break;
                            }
                        }

                        String msg = queue.take();
                        synchronized (LogService.this){
                            --reservations;
                        }
                        writer.print(msg);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }finally {
                writer.close();
            }
        }
    }
}
