package com.algorithm.concurrent.executer;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;

/**
 * @program algorithm
 * @description:工作队列填满后，使用SemaPhore控制任务提交速率
 * @author: liangshaofeng
 * @create: 2019/07/02 13:07
 */
public class BoundedExecutor {

    private final Executor executor;
    private final Semaphore semaphore;

    public BoundedExecutor(Executor executor, Semaphore semaphore) {
        this.executor = executor;
        this.semaphore = semaphore;
    }

    private void submitTask(final Runnable commond) throws InterruptedException {
        semaphore.acquire();
        try {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {

                        commond.run();
                    }finally {
                        semaphore.release();
                    }
                }
            });
        } catch (RejectedExecutionException e){
            semaphore.release();
        }
    }
}
