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

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * RandomAccessFile
 *
 */
public class MultiThreadDownload {
    // 定义下载资源的路径
    private String path;
    // 指定所下载的文件的保存位置
    private String targetFile;
    // 定义需要使用多少线程下载资源
    private int threadNum;
    // 定义下载的线程对象
    private DownThread[] threads;
    // 定义下载的文件的总大小
    private int fileSize;

    public MultiThreadDownload(String path, String targetFile, int threadNum)
    {
        this.path = path;
        this.threadNum = threadNum;
        // 初始化threads数组
        threads = new DownThread[threadNum];
        this.targetFile = targetFile;
    }

    public void download() throws Exception
    {
//        TrustManager[] tm = { new MyX509TrustManager() };
//        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
//        sslContext.init(null, tm, new java.security.SecureRandom());
//        // 从上述SSLContext对象中得到SSLSocketFactory对象
//        javax.net.ssl.SSLSocketFactory ssf = sslContext.getSocketFactory();

        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        conn.setRequestProperty(
                "Accept",
                "image/gif, image/jpeg, image/pjpeg, image/pjpeg, "
                        + "application/x-shockwave-flash, application/xaml+xml, "
                        + "application/vnd.ms-xpsdocument, application/x-ms-xbap, "
                        + "application/x-ms-application, application/vnd.ms-excel, "
                        + "application/vnd.ms-powerpoint, application/msword, */*");
        conn.setRequestProperty("Accept-Language", "zh-CN");
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("Connection", "Keep-Alive");
        // 得到文件大小
        fileSize = conn.getContentLength();
        conn.disconnect();
        int currentPartSize = fileSize / threadNum + 1;
        RandomAccessFile file = new RandomAccessFile(targetFile, "rw");
        // 设置本地文件的大小
        file.setLength(fileSize);
        file.close();

        for (int i = 0; i < threadNum; i++)
        {
            // 计算每条线程的下载的开始位置
            int startPos = i * currentPartSize;
            // 每个线程使用一个RandomAccessFile进行下载
            RandomAccessFile currentPart = new RandomAccessFile(targetFile,
                    "rw");
            // 定位该线程的下载位置
            currentPart.seek(startPos);
            // 创建下载线程
            threads[i] = new DownThread(startPos, currentPartSize,
                    currentPart);
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
        int sumSize = 0;
        for (int i = 0; i < threadNum; i++)
        {
            sumSize += threads[i].length;
        }
        // 返回已经完成的百分比
        return sumSize * 100.00 / fileSize;
    }

    private class DownThread extends Thread
    {
        // 当前线程的下载位置
        private int startPos;
        // 定义当前线程负责下载的文件大小
        private int currentPartSize;
        // 当前线程需要下载的文件块
        private RandomAccessFile currentPart;
        // 定义已经该线程已下载的字节数
        public int length;

        public DownThread(int startPos, int currentPartSize,
                          RandomAccessFile currentPart)
        {
            this.startPos = startPos;
            this.currentPartSize = currentPartSize;
            this.currentPart = currentPart;
        }

        @Override
        public void run()
        {
            try
            {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection)url
                        .openConnection();
                conn.setConnectTimeout(5 * 1000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty(
                        "Accept",
                        "image/gif, image/jpeg, image/pjpeg, image/pjpeg, "
                                + "application/x-shockwave-flash, application/xaml+xml, "
                                + "application/vnd.ms-xpsdocument, application/x-ms-xbap, "
                                + "application/x-ms-application, application/vnd.ms-excel, "
                                + "application/vnd.ms-powerpoint, application/msword, */*");
                conn.setRequestProperty("Accept-Language", "zh-CN");
                conn.setRequestProperty("Charset", "UTF-8");
                InputStream inStream = conn.getInputStream();
                // 跳过startPos个字节，表明该线程只下载自己负责哪部分文件。
                inStream.skip(this.startPos);
                byte[] buffer = new byte[1024];
                int hasRead = 0;
                // 读取网络数据，并写入本地文件
                while (length < currentPartSize
                        && (hasRead = inStream.read(buffer)) > 0)
                {
//                    if(length+hasRead<=currentPartSize){
                        currentPart.write(buffer, 0, hasRead);
//                    }else {
//                        currentPart.write(buffer, 0, Math.min( currentPartSize-length,hasRead ));
//                    }
                    // 累计该线程下载的总大小
//                    length += Math.min( currentPartSize-length,hasRead );
                    length += hasRead;
                }
                currentPart.close();
                inStream.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void main( String[] args ) throws Exception {
//        java.net.MalformedURLException: unknown protocol: d   加上前缀file:///
//        10线程下载耗时628634ms
        //实践发现对于小文件多线程下载实际上更慢
        MultiThreadDownload multiThreadDownload = new MultiThreadDownload( "https://github-production-release-asset-2e65be.s3.amazonaws.com/924551/ee5da980-79e8-11e9-98e5-363843987740?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAIWNJYAX4CSVEH53A%2F20190523%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20190523T094900Z&X-Amz-Expires=300&X-Amz-Signature=c0922ec4c11fefcd663bd38b8f417bfbc25680901d35685af28d9f04399d447a&X-Amz-SignedHeaders=host&actor_id=30116609&response-content-disposition=attachment%3B%20filename%3Drabbitmq-server-3.7.15.exe&response-content-type=application%2Foctet-stream",
                "D:\\Users\\Documents\\Tencent Files\\2558244812\\FileRecv\\2ac4b244ad345982a27751b602f431a(2)",2 );
        multiThreadDownload.download();
    }
}