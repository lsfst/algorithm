package com.algorithm.concurrent.thread;

/**
 * @program algorithm
 * @description:
 * @author: liangshaofeng
 * @create: 2019/04/22 23:13
 */
public class ThreadLocalTest {
    static ThreadLocal<String> localVariable = new ThreadLocal<>();
    static void print(String str){
        //打印本地线程中的变量
        System.out.println(str+":"+localVariable.get());
        //移除本地线程中的变量
//        localVariable.remove();
    }
    public static void main(String[] args) {
            Thread threadOne = new Thread(()->{
                localVariable.set("threadOne local variable");
                print("threadOne");

                System.out.println("threadOne remove after "+":"+localVariable.get());

            });

        Thread threadTwo = new Thread(()->{
            localVariable.set("threadTwo local variable");
            print("threadTwo");

            System.out.println("threadTwo remove after "+":"+localVariable.get());

        });

        threadOne.start();
        threadTwo.start();
    }


}
