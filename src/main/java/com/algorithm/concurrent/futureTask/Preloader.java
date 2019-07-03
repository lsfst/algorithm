package com.algorithm.concurrent.futureTask;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @program algorithm
 * @description:提前加载数据
 * @author: liangshaofeng
 * @create: 2019/06/27 23:28
 */
public class Preloader {
    private final FutureTask<JSONObject> future = new FutureTask<>(new Callable<JSONObject>() {
        @Override
        public JSONObject call() throws Exception {
            return loadInfo();
        }
    });
    private final Thread thread = new Thread(future);

    public void start(){
        thread.start();
    }

    public JSONObject get(){
        try {
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            //可能是Callable的受检异常，RuntimeExecption,Error
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject loadInfo(){
        return new JSONObject();
    }
}
