package com.algorithm.nio.FileChannell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;

/**
 * @program algorithm
 * @description: FileChannel 直接操作内核空间内存，省去数据从内核空间到用户空间的复制损耗
 * @author: liangshaofeng
 * @create: 2019/05/22 23:15
 */
public class FileChannel {
    public static void main(String[] args) {
        int BUFFER_SIZE = 1024;
        String fileName ="test.db";
        long filelen = new File(fileName).length();
        int buffercount = 1+(int)(filelen/BUFFER_SIZE);

        MappedByteBuffer[] buffers = new MappedByteBuffer[buffercount];
        long remaining = filelen;

        for(int i = 0;i<buffercount;i++){
            RandomAccessFile file;
            try {
                file = new RandomAccessFile(fileName,"r");
                buffers[i] = file.getChannel().map(java.nio.channels.FileChannel.MapMode.READ_ONLY,
                        i*BUFFER_SIZE,(int)Math.min(remaining,BUFFER_SIZE));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            remaining -= BUFFER_SIZE;
        }
    }
}
