package com.algorithm.concurrent.puzzle;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @program algorithm
 * @description:串行解决方案：深度遍历,找到方案后结束（不一定是最短方案），受限于栈大小限制
 * @author: liangshaofeng
 * @create: 2019/07/02 14:13
 */
public class SequentialPuzzleSolver<P,M> {
    private final Puzzle<P,M> puzzle;
    private final Set<P> seen = new HashSet<>();

    public SequentialPuzzleSolver(Puzzle<P, M> puzzle) {
        this.puzzle = puzzle;
    }

    public List<M> solve(){
        P pos = puzzle.initiallPosition();
        return search(new Node<>(pos,null,null));
    }

    private List<M> search(Node<P,M> node){
        if(!seen.contains(node.pos)){
            seen.add(node.pos);
            if(puzzle.isGoal(node.pos)){
                return node.asMoveList();
            }
            for(M move : puzzle.leaglMoves(node.pos)){
                P pos = puzzle.move(node.pos,move);
                Node<P,M> child = new Node<P,M>(pos,move,node);
                List<M> res = search(child);
                if(res!=null){
                    return res;
                }
            }
        }
        return null;
    }

}
