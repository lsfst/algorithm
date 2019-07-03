package com.algorithm.concurrent.executer;

import java.util.concurrent.Executor;

/**
 * @program algorithm
 * @description:以同步方式执行任务的Executor
 * @author: liangshaofeng
 * @create: 2019/06/29 13:22
 */
public class WithinThreadExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
