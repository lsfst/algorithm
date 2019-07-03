package com.algorithm.concurrent.reentrantLockList;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program algorithm
 * @description:独占锁维护线程安全的list
 * @author: liangshaofeng
 * @create: 2019/06/27 10:36
 */
public class ReentrantLockList {

    private ArrayList<String> array = new ArrayList<>();
    private volatile ReentrantLock lock = new ReentrantLock();

    public void add(String e){
        lock.lock();
        try {
            array.add(e);

        }finally {
            lock.unlock();

        }
    }

    public void remove(String e){

        lock.lock();
        try {
            array.remove(e);

        }finally {
            lock.unlock();

        }
    }

    public String get(int index){

        lock.lock();
        try {
            return array.get(index);

        }finally {
            lock.unlock();

        }
    }

}
