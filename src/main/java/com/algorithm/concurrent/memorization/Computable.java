package com.algorithm.concurrent.memorization;

public interface Computable<A,V> {
    V compute(A arg) throws InterruptedException;
}
