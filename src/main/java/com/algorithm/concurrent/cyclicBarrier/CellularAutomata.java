package com.algorithm.concurrent.cyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @program algorithm
 * @description:问题分解->多线程完成->结果合并
 * @author: liangshaofeng
 * @create: 2019/06/27 23:53
 */
public class CellularAutomata { private final Board mainBoard;
    private final CyclicBarrier barrier;
    private final Worker[] workers;

    public CellularAutomata(Board mainBoard) {
        this.mainBoard = mainBoard;
        int count = Runtime.getRuntime().availableProcessors();
        this.barrier=new CyclicBarrier(count, new Runnable() {
            @Override
            public void run() {
                //
            }
        });
        this.workers=new Worker[count];
        for(int i=0;i<count;i++){
            workers[i] = new Worker(new Board());
        }
    }

    public void start(){
        for (int i = 0;i<workers.length;i++){
            new Thread(workers[i]).start();
        }
        //...
    }

    class Worker implements Runnable{
        private final Board board;

        public Worker(Board board) {
            this.board = board;
        }


        @Override
        public void run() {
            while (!board.hasConverged()){
                for (int x = 0;x<board.getMaxX();x++){
                    for (int y = 0;y<board.getMaxY();y++){
                        //
                        try {
                            barrier.await();
                        } catch (InterruptedException e) {
                            return;
                        } catch (BrokenBarrierException e) {
                            return;
                        }
                    }
                }
            }
        }
    }

    class Board{
        public boolean hasConverged(){
            return false;
        }

        public int getMaxX(){
            return 10;
        }

        public int getMaxY(){
            return 10;
        }
    }
}
