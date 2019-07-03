package com.algorithm.concurrent.filecrawler;

import java.io.File;
import java.util.concurrent.BlockingQueue;

/**
 * @program algorithm
 * @description:
 * @author: liangshaofeng
 * @create: 2019/06/27 17:29
 */
public class Indexer implements Runnable {
    private final BlockingQueue<File> queue;

    public Indexer(BlockingQueue<File> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true){
                indexFile(queue.take());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    public void indexFile(File file){

    }
}
