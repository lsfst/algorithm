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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

/**
 * RandomAccessFile是Java中输入，输出流体系中功能最丰富的文件内容访问类，它提供很多方法来操作文件，包括读写支持，与普通的IO流相比，它最大的特别之处就是支持任意访问的方式，程序可以直接跳到任意地方来读写数据。
 *
 * 如果我们只希望访问文件的部分内容，而不是把文件从头读到尾，使用RandomAccessFile将会带来更简洁的代码以及更好的性能
 *
 * getFilePointer()	返回文件记录指针的当前位置
 *
 * seek(long pos)	将文件记录指针定位到pos的位置
 */
public class RandomAccessFileUtil {

    /**
     * 随机读取
     * @param path 文件路径
     * @param pointe 指针位置
     * **/
    public static void randomRed(String path,int pointe){
        try{
            //RandomAccessFile raf=new RandomAccessFile(new File("D:\\3\\test.txt"), "r");
            /**
             * model各个参数详解
             * r 代表以只读方式打开指定文件
             * rw 以读写方式打开指定文件
             * rws 读写方式打开，并对内容或元数据都同步写入底层存储设备
             * rwd 读写方式打开，对文件内容的更新同步更新至底层存储设备
             *
             * **/
            RandomAccessFile raf=new RandomAccessFile(path, "r");
            //获取RandomAccessFile对象文件指针的位置，初始位置是0
            System.out.println("RandomAccessFile文件指针的初始位置:"+raf.getFilePointer());
            raf.seek(pointe);//移动文件指针位置
            byte[]  buff=new byte[1024];
            //用于保存实际读取的字节数
            int hasRead=0;
            //循环读取
            while((hasRead=raf.read(buff))>0){
                //打印读取的内容,并将字节转为字符串输入
                System.out.println(new String(buff,0,hasRead));

            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }


    /**
     * 追加数据
     * 写的方法
     * @param path 文件路径
     * ***/
    public static void randomWrite(String path){
        try{
            /**以读写的方式建立一个RandomAccessFile对象**/
            RandomAccessFile raf=new RandomAccessFile(path, "rw");

            //将记录指针移动到文件最后
            raf.seek(raf.length());
            raf.write("我是追加的 \r\n".getBytes());

        }catch(Exception e){
            e.printStackTrace();
        }

    }


    /**
     * 随机插入
     * 插入数据
     * @param fileName 文件名
     * @param points 指针位置
     * @param insertContent 插入内容
     * **/
    public static void insert(String fileName,long points,String insertContent){
        try{
            File tmp= File.createTempFile("tmp", null);
            tmp.deleteOnExit();//在JVM退出时删除

            RandomAccessFile raf=new RandomAccessFile(fileName, "rw");
            //创建一个临时文件夹来保存插入点后的数据
            FileOutputStream tmpOut=new FileOutputStream(tmp);
            FileInputStream tmpIn=new FileInputStream(tmp);
            raf.seek(points);
            /**将插入点后的内容读入临时文件夹**/

            byte [] buff=new byte[1024];
            //用于保存临时读取的字节数
            int hasRead=0;
            //循环读取插入点后的内容
            while((hasRead=raf.read(buff))>0){
                // 将读取的数据写入临时文件中
                tmpOut.write(buff, 0, hasRead);
            }

            //插入需要指定添加的数据
            raf.seek(points);//返回原来的插入处
            //追加需要追加的内容
            raf.write(insertContent.getBytes());
            //最后追加临时文件中的内容
            while((hasRead=tmpIn.read(buff))>0){
                raf.write(buff,0,hasRead);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}