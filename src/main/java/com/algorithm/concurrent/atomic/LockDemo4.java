package com.algorithm.concurrent.atomic;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @program algorithm
 * @description:自定义实现线程安全
 * 操作内存需要与硬件打交道（CAS）
 * jvm不直接操作内存，unsafe可以修改对象值，属性，数组，class等
 * @author: liangshaofeng
 * @create: 2019/04/14 21:31
 */
public class LockDemo4 {
    //正常无法生成unsafe，但可以通过反射
    static Unsafe unsafe = null;
    private static long valueOff;
    volatile int i = 0;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);

            //通过unsafe去调用底层硬件原语
            //无法直接操作内存，可以通过对象中属性的偏移量，去修改值
            valueOff = unsafe.objectFieldOffset(LockDemo4.class.getDeclaredField("i"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void add(){
        //cas多线程下有几率失败，必须循环
        int current;
        do {
            current=unsafe.getIntVolatile(this,valueOff);
        }while ( !unsafe.compareAndSwapInt(this,valueOff,current,current+1));

    }


    public static void main(String[] args) throws InterruptedException {
        LockDemo4 id = new LockDemo4();
        long start = System.currentTimeMillis();
        for(int i = 0 ;i<2;i++){
            new Thread(()->{
                for(int j = 0;j<10000;j++){
                    id.add();
                }
            }).start();
        }
        long end = System.currentTimeMillis();
        Thread.sleep(2000L);

        System.out.println(id.i+ "  "+(end-start)+"ms");
    }
}
