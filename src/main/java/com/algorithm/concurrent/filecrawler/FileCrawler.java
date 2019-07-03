package com.algorithm.concurrent.filecrawler;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @program algorithm
 * @description:桌面搜索，文件索引服务
 * @author: liangshaofeng
 * @create: 2019/06/27 17:27
 */
public class FileCrawler implements Runnable {
    private final BlockingQueue<File> fileQueue;
    private final FileFilter fileFilter;
    private final File root;

    public FileCrawler(BlockingQueue<File> fileQueue, FileFilter fileFilter, File root) {
        this.fileQueue = fileQueue;
        this.fileFilter = fileFilter;
        this.root = root;
    }

    @Override
    public void run() {
        try {
            crawl(root);
        }catch (InterruptedException e){
            //恢复中断状态
            Thread.currentThread().interrupt();
        }
    }

    public void crawl(File root) throws InterruptedException{
        File[] files = root.listFiles();
        if(files!=null){
            for(File file:files){
                if(file.isDirectory()){
                    crawl(file);
                }else if(!alreadyIndexed(file)){
                    fileQueue.add(file);
                }
            }
        }
    }

    public boolean alreadyIndexed(File file){
        return false;
    }

    public static void main(String[] args) {
        startIndexing(new File[]{});
    }

    public static void startIndexing(File[] roots){
        BlockingQueue<File> queue = new LinkedBlockingQueue<>(1000);
        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        };

        for(File root:roots){
            new Thread(new FileCrawler(queue,filter,root)).start();
        }

        for(File root:roots){
            new Thread(new Indexer(queue)).start();
        }
    }
}
