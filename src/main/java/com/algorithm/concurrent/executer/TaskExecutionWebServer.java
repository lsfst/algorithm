package com.algorithm.concurrent.executer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @program algorithm
 * @description:基于线程池的服务器
 * @author: liangshaofeng
 * @create: 2019/06/29 13:17
 */
public class TaskExecutionWebServer {
    private static final int NTHREADS = 100;
    private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (true){
            final Socket con = socket.accept();
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    //
                }
            };
            exec.execute(task);
        }
    }
}
