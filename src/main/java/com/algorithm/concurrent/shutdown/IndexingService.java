package com.algorithm.concurrent.shutdown;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;

/**
 * @program algorithm
 * @description:毒丸：在生产者和消费者数量已知时使用
 * 多生产者扩展：每个生产者一个毒丸，所有毒丸都接收到时才停止，限定无界队列
 * @author: liangshaofeng
 * @create: 2019/06/29 16:51
 */
public class IndexingService {
    private static final File POISON = new File("...");
    private final IndexerThread consumer = new IndexerThread();
    private final CrawlerThread producer = new CrawlerThread();
    private final BlockingQueue<File> queue;
    private final FileFilter fileFilter;
    private final File root;

    public IndexingService(BlockingQueue<File> queue, FileFilter fileFilter, File root) {
        this.queue = queue;
        this.fileFilter = fileFilter;
        this.root = root;
    }

    class CrawlerThread extends Thread {
        @Override
        public void run() {
            try {
                crawl(root);
            }finally {
                while (true){
                    try {
                        queue.put(POISON);
                        break;
                    } catch (InterruptedException e) {
                        //retry
                    }
                }
            }
        }

        private void crawl(File root){
            //...
        }
    }


    class IndexerThread extends Thread {
        @Override
        public void run() {
           while (true){
               File file = null;
               try {
                   file = queue.take();

               if(file==POISON){
                   break;
               }else {
                   indexFile(file);
               }
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        }

        private void indexFile(File root){
            //...
        }
    }
}
