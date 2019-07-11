package com.algorithm.rpc.hello;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @program algorithm
 * @description:
 * @author: liangshaofeng
 * @create: 2019/07/07 22:17
 */
public class Client<T> {
    public static <T> T get(final Class<?> serviceInterface, final InetSocketAddress address){
        T instance = (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(),
                 new Class<? >[]{serviceInterface},
        new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = null;
                ObjectOutputStream outputStream = null;
                ObjectInputStream inputStream = null;
                try {
                    socket = new Socket();
                    socket.connect(address);
                    outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.writeUTF(serviceInterface.getName());
                    outputStream.writeUTF(method.getName());
                    outputStream.writeObject(method.getParameterTypes());
                    outputStream.writeObject(args);

                    inputStream = new ObjectInputStream(socket.getInputStream());
                    return inputStream.readObject();
                }finally {
                    try {
                        if(socket!=null){
                            socket.close();}
                        if(inputStream!=null){
                            inputStream.close();}
                        if(outputStream!=null){
                            outputStream.close();}
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return instance;
    }

    public static void main(String[] args) {
        HelloService service = Client.get(HelloService.class,new InetSocketAddress("localhost",8020));
        System.out.println(service.Hello("RPC"));
    }
}
