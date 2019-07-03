package com.algorithm.concurrent.executer;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @program algorithm
 * @description:
 * @author: liangshaofeng
 * @create: 2019/06/29 17:23
 */
public class ThreadDeadlock {
    ExecutorService exec = Executors.newSingleThreadExecutor();

    class RenderPageTask implements Callable<String>{

        @Override
        public String call() throws Exception {
            Future<String> header,footer;
            header = exec.submit(new LoadFileTask("header.html"));
            footer = exec.submit(new LoadFileTask("footer.html"));
            //将发生死锁
            return header.get()+footer.get();
        }
    }


    class LoadFileTask implements Callable<String>{
        private String file;

        public LoadFileTask(String file) {
            this.file = file;
        }

        @Override
        public String call() throws Exception {
            //...
            return null;
        }
    }
}
