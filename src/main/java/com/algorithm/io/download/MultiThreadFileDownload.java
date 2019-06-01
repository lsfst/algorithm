/**
 * algorithm 版权声明
 * Copyright (c) 2019, EouTech All rights reserved
 *
 * @brief 文件说明
 * <p>
 * TODO 本文件功能作用详细说明
 * <p>
 * @history 修订说明
 * 20190523    lsf     初始版本
 */
package com.algorithm.io.download;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * @brief TODO 类功能作用及实现逻辑说明
 * @see
 */
public class MultiThreadFileDownload {

    // 定义下载资源的路径
    private String path;
    // 指定所下载的文件的保存位置
    private String targetFile;
    // 定义需要使用多少线程下载资源
    private int threadNum;
    // 定义下载的线程对象
    private DownLoadThread[] threads;
    // 定义下载的文件的总大小
    private long fileSize;

    public MultiThreadFileDownload(String path, String targetFile, int threadNum)
    {
        this.path = path;
        this.threadNum = threadNum;
        // 初始化threads数组
        threads = new DownLoadThread[threadNum];
        this.targetFile = targetFile;
    }

    public void download() throws Exception
    {
        // 得到文件大小
        File source = new File( this.path );
        fileSize = source.length();
        long currentPartSize = fileSize / threadNum + 1;
        RandomAccessFile file = new RandomAccessFile(targetFile, "rw");
        // 设置本地文件的大小
        file.setLength(fileSize);
        file.close();

        for (int i = 0; i < threadNum; i++)
        {
            // 计算每条线程的下载的开始位置
            long startPos = i * currentPartSize;
            // 每个线程使用一个RandomAccessFile进行下载
            File currentPart = new File(targetFile);
            File sourcePart = new File(path);
            // 创建下载线程
            threads[i] = new DownLoadThread(startPos,sourcePart,currentPart,currentPartSize);
            // 启动下载线程
            threads[i].start();
        }

        new Thread( ()->{
            long start = System.currentTimeMillis();
            while ( getCompleteRate()<100 ){
                System.out.println(getCompleteRate());
                try {
                    Thread.sleep( 100 );
                } catch ( InterruptedException e ) {
                    e.printStackTrace();
                }
            }
            long end = System.currentTimeMillis();
            System.out.println(getCompleteRate());
            System.out.println("下载耗时"+(end-start)+"ms");
        } ).start();
    }

    // 获取下载的完成百分比
    public double getCompleteRate()
    {
        // 统计多条线程已经下载的总大小
        long sumSize = 0;
        for (int i = 0; i < threadNum; i++)
        {
            sumSize += threads[i].length;
        }
        // 返回已经完成的百分比
        return sumSize * 100.00 / fileSize;
    }

    static class DownLoadThread extends Thread {
        private long startPos;
        private File src;
        private long currentPartSize;
        private File desc;
        // 定义已经该线程已下载的字节数
        public volatile long length;

        /**
         * @param startPos            开始下载的位置
         * @param src              要下载的文件
         * @param desc             要下载的目的地
         * @param currentPartSize            要下载的总量
         */
        public DownLoadThread( long startPos, File src, File desc, long currentPartSize ) {
            this.startPos = startPos;
            this.src = src;
            this.desc = desc;
            this.currentPartSize = currentPartSize;
        }

        @Override
        public void run() {
            try {
                // 创建输入流关联源,因为要指定位置读和写,所以我们需要用随机访问流
                RandomAccessFile src = new RandomAccessFile( this.src, "rw" );
                RandomAccessFile desc = new RandomAccessFile( this.desc, "rw" );
                // 源和目的都要从start开始
                src.seek( startPos );
                desc.seek( startPos );
                // 开始读写
                byte[] arr = new byte[ 1024 ];
                int len;
                while ( ( len = src.read( arr ) ) != -1 ) {
                    //分三种情况
                    if ( len + length > currentPartSize ) {
                        //1.当读取的时候操作自己该线程的下载总量的时候,需要改变len
                        len = ( int ) ( currentPartSize - length );
                        desc.write( arr, 0, len );
                        length+=len;
                    //证明该线程下载任务已经完毕,结束读写操作
                        break;
                    } else if ( len + length < currentPartSize ) {
                    //2.证明还没有到下载总量,直接将内容写入
                        desc.write( arr, 0, len );
                    //并且使计数器任务累加
                        length += arr.length;
                    } else {
                    //3.证明改好到下载总量
                        desc.write( arr, 0, len );
                        length+=len;
                    //结束读写
                        break;
                    }
                }
                src.close();
                desc.close();
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
    }

    public static void main( String[] args ) throws Exception {
        //sagit_images_V10.3.1.0.OCACNXM_20190328.0000.00_8.0_cn_38e230279a.tgz(2G)
        //5线程：139116ms  3线程 97466ms 2线程：82180ms  1线程：82895ms
        // apache-jmeter-5.1.1.zip(58M) 1线程:610 2线程：410 3线程：310 5线程:310
        MultiThreadFileDownload multiThreadFileDownload = new MultiThreadFileDownload( "D:\\Users\\Downloads\\sagit_images_V10.3.1.0.OCACNXM_20190328.0000.00_8.0_cn_38e230279a.tgz",
                "D:\\Users\\Downloads\\sagit_images_V10.3.1.0.OCACNXM_20190328.0000.00_8.0_cn_38e230279a.tgz(2)",1 );
        multiThreadFileDownload.download();

    }
}