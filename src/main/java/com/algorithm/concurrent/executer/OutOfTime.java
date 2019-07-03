package com.algorithm.concurrent.executer;

import com.algorithm.concurrent.countdownlatch.Run;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @program algorithm
 * @description:Timer无法捕获异常，也无法回复线程执行，抛出未受检的异常时会终止线程
 * @author: liangshaofeng
 * @create: 2019/06/29 13:37
 */
public class OutOfTime {

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new ThrowWork(),1);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.schedule(new ThrowWork(),1);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class ThrowWork extends TimerTask{

        @Override
        public void run() {
            throw new RuntimeException();
        }
    }
}
