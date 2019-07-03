package com.algorithm.proxy;

/**
 * @program algorithm
 * @description: 静态代理：冗杂，为每个委托类生成代理类实现对象
 * @author: liangshaofeng
 * @create: 2019/05/25 13:45
 */
public class CalcProxy implements Calc {

    private Calc calc;

    CalcProxy(Calc calc){
        this.calc=calc;
    }

    @Override
    public int add(int a, int b) {
        //before
        int res = calc.add(a,b);
        //after
        return res;
    }
}
