package com.algorithm.concurrent.executer;

import java.util.concurrent.Executor;

/**
 * @program algorithm
 * @description:为每个请求启动新线程的Executor
 * @author: liangshaofeng
 * @create: 2019/06/29 13:20
 */
public class ThreadPerTaskExecutor  implements Executor {
    @Override
    public void execute(Runnable command) {
        new Thread(command).run();
    }
}
