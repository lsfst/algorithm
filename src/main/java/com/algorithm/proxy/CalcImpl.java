package com.algorithm.proxy;

/**
 * @program algorithm
 * @description:
 * @author: liangshaofeng
 * @create: 2019/05/25 13:44
 */
public class CalcImpl implements Calc {
    @Override
    public int add(int a, int b) {
        System.out.println(a+b);
        return a+b;
    }
}
