package com.algorithm.concurrent.interrupt;


import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @program algorithm
 * @description:改写interrupt将非标准的取消操作封装在Thread中
 * @author: liangshaofeng
 * @create: 2019/06/29 16:22
 */
public class ReadThread extends Thread {
    private final Socket socket;
    private final InputStream in;

    public ReadThread(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
    }

    @Override
    public void interrupt() {
        try {
            socket.close();
        } catch (IOException e) {
            //忽略
            e.printStackTrace();
        }finally {
            super.interrupt();
        }
    }

    public void run(){
        try {
            byte[] buf = new byte[1024];
            while (true){
                int count = in.read(buf);
                if(count<0){
                    break;
                }else if(count>0){
                    //处理buf
                }
            }
        } catch (IOException e) {
            //允许线程退出
            e.printStackTrace();
        }
    }
}
