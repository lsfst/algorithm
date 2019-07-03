package com.algorithm.concurrent.puzzle;

import java.util.Set;

/**
 *  迷宫问题
 * @param <P>
 * @param <M>
 */
public interface Puzzle<P,M> {
    P initiallPosition();
    boolean isGoal(P position);
    Set<M> leaglMoves(P position);
    P move(P position,M move);
}
