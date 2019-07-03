package com.algorithm.concurrent;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @program algorithm
 * @description:
 * @author: liangshaofeng
 * @create: 2019/05/19 11:21
 */
public class MultiThread {
    public static void main(String[] args) {
        ThreadMXBean threadMXBean=ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos=threadMXBean.dumpAllThreads(false,false);
        for(ThreadInfo info:threadInfos){
            System.out.println(info.getThreadId()+" "+info.getThreadName());
        }
    }
}
