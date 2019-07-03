package com.algorithm.concurrent.exchanger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * @program algorithm
 * @description: Exchanger：线程间进行数据交换
 * @author: liangshaofeng
 * @create: 2019/05/25 13:34
 */
public class Test {
    public static void main(String[] args) {
        final Exchanger<List<Integer>> exchanger = new Exchanger<>();
        new Thread(()->{
            List<Integer> l = new ArrayList<>(2);
            l.add(1);
            l.add(2);
            try {
                l = exchanger.exchange(l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread1"+l);
        }).start();
        new Thread(()->{
            List<Integer> l = new ArrayList<>(2);
            l.add(4);
            l.add(5);
            try {
                l = exchanger.exchange(l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread2"+l);
        }).start();
     }
}
