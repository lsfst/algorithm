package com.algorithm.concurrent.efficienceTest;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program algorithm
 * @description:
 * @author: liangshaofeng
 * @create: 2019/05/25 12:20
 */
public class AtomicTest {

    static class Count1{
        private int count = 0;
        public int increase(){
            synchronized (this){
                count++;
                return count;
            }
        }

        public int decrease(){
            synchronized (this){
                count--;
                return count;
            }
        }
    }


    static class Count2{
        private AtomicInteger count = new AtomicInteger();

        public int increase(){
            return count.incrementAndGet();
        }

        public int decrease(){
            return count.decrementAndGet();
        }
    }

    public static void main(String[] args) {
        //将近4倍性能差距
        Count1 count1 = new Count1();
        Count2 count2 = new Count2();
        long start = System.currentTimeMillis();
        for(int i = 0;i<100000000;i++){
            count1.increase();
        }
        System.out.println(System.currentTimeMillis()-start);
        start=System.currentTimeMillis();
        for(int i = 0;i<100000000;i++){
            count2.increase();
        }
        System.out.println(System.currentTimeMillis()-start);
    }
}
