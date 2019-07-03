package com.algorithm.concurrent.connectionPool;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program algorithm
 * @description:可能是机器性能太好，1000个连接都没出现超时
 * @author: liangshaofeng
 * @create: 2019/07/02 23:14
 */
public class ConnectionPoolTest {
    static int initSize=100;
    static ConnectionPool pool = new ConnectionPool(initSize);
    //保证所有ConnectionRunner能同时开始
    static CountDownLatch start = new CountDownLatch(1);
    //等待所有connectionRunner结束后才继续执行
    static CountDownLatch end;

    public static void main(String[] args) throws InterruptedException {
        int threadCount = initSize;
        end = new CountDownLatch(threadCount);
        int count =20;
        AtomicInteger got = new AtomicInteger();
        AtomicInteger notGot = new AtomicInteger();
        for(int i = 0;i<threadCount;i++){
            Thread thread = new Thread(new ConnectionRunner(count,got,notGot),"ConnectionRunnerThread");
            thread.start();
        }
        start.countDown();
        end.await();
        System.out.println("total invoke :"+(threadCount*count));
        System.out.println("got connection :"+got);
        System.out.println("not got connection :"+notGot);
    }


    static class ConnectionRunner implements Runnable{
        int count;
        AtomicInteger got;
        AtomicInteger notGot;

        public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        @Override
        public void run() {
            try {
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (count>0){
                try {
                    Connection connection = pool.fetchConnection(1000);
                    if(connection!=null){
                        try {
                            connection.createStatement();
                            connection.commit();
                        }finally {
                            pool.releaseConnection(connection);
                            got.incrementAndGet();
                        }
                    }else {
                        notGot.incrementAndGet();
                    }
                }catch (Exception e){

                }finally {
                    count--;
                }
            }
            end.countDown();
        }
    }


}
