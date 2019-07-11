package com.algorithm.concurrent.memorization;

import java.math.BigInteger;

/**
 * @program algorithm
 * @description:
 * @author: liangshaofeng
 * @create: 2019/06/28 22:56
 */
public class ExpensiveFunction implements Computable<String,BigInteger> {
    @Override
    public BigInteger get(String arg) throws InterruptedException {
        return new BigInteger(arg);
    }
}
