package com.algorithm.rpc.hello;

/**
 * @program algorithm
 * @description:
 * @author: liangshaofeng
 * @create: 2019/07/07 21:45
 */
public class HelloServiceImple implements HelloService {
    @Override
    public String Hello(String name) {
        System.out.println("收到消息："+name);
        return "你好，"+name;
    }
}
