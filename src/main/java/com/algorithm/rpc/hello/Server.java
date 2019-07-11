package com.algorithm.rpc.hello;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program algorithm
 * @description:
 * @author: liangshaofeng
 * @create: 2019/07/07 21:49
 */
public class Server {
    private static ExecutorService executor = Executors.newFixedThreadPool(10);
    private static final HashMap<String,Class> serviceRegistry = new HashMap<>();
    public void registry(Class serviceInterface,Class impl){
        serviceRegistry.put(serviceInterface.getName(),impl);
    }

    public void start(int port) throws IOException {
        final ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(port));
        System.out.println("服务已启动");
        while (true){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    Socket socket = null;
                    ObjectInputStream input = null;
                    ObjectOutputStream output = null;
                    try {
                        socket = server.accept();
                        input = new ObjectInputStream(socket.getInputStream());
                        output = new ObjectOutputStream(socket.getOutputStream());
                        String serviceName = input.readUTF();
                        String methodName = input.readUTF();
                        Class<?> [] parameterTypes = (Class<?>[]) input.readObject();
                        Object[] args = (Object[]) input.readObject();
                        Class serviceClass = serviceRegistry.get(serviceName);
                        if (serviceClass == null){
                            throw new ClassNotFoundException(serviceName+" not found");
                        }

                        Method method = serviceClass.getMethod(methodName,parameterTypes);
                        Object res = method.invoke(serviceClass.newInstance(),args);
                        output.writeObject(res);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            if(socket!=null){
                                    socket.close();}
                            if(input!=null){
                                input.close();}
                            if(output!=null){
                                output.close();}
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        }

    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.registry(HelloService.class,HelloServiceImple.class);
        server.start(8020);
    }
}
