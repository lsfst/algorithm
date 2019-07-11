package com.algorithm.concurrent.memorization;

public interface Computable<A,V> {
    V get(A arg) throws InterruptedException;
}
