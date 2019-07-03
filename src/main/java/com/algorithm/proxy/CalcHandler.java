package com.algorithm.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @program algorithm
 * @description:
 * @author: liangshaofeng
 * @create: 2019/05/25 13:49
 */
public class CalcHandler implements InvocationHandler {
    Object object;
    CalcHandler(Object o){
        this.object = o;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        this.doBefore();
        Object o = method.invoke(object,args);
        this.doAfter();
        return o;
    }

    public void doBefore(){
        System.out.println("do before");
    }

    public void doAfter(){
        System.out.println("do after");
    }

    public static void main(String[] args) {
        Calc calc = new CalcImpl();
        CalcHandler calcHandler = new CalcHandler(calc);

        Calc proxy = (Calc) Proxy.newProxyInstance(calc.getClass().getClassLoader(),calc.getClass().getInterfaces(),calcHandler);

        proxy.add(1,2);
    }
}
