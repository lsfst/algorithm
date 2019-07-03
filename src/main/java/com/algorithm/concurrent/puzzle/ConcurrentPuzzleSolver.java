//package com.algorithm.concurrent.puzzle;
//
//import javafx.concurrent.Task;
//
//import java.util.List;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.Executor;
//import java.util.concurrent.ExecutorService;
//
///**
// * @program algorithm
// * @description:并发搜索，广度优先
// * @author: liangshaofeng
// * @create: 2019/07/02 14:24
// */
//public class ConcurrentPuzzleSolver<P,M> {
//    private final Puzzle<P,M> puzzle;
//    private final ExecutorService exec;
//    private final ConcurrentHashMap<P,Boolean> seen;
//    final ValueLatch<Node<P,M>> solution = new ValueLatch<>();
//
//    public ConcurrentPuzzleSolver(Puzzle<P, M> puzzle, ExecutorService exec, ConcurrentHashMap<P, Boolean> seen) {
//        this.puzzle = puzzle;
//        this.exec = exec;
//        this.seen = seen;
//    }
//
//    public List<M> solve() throws InterruptedException {
//        try {
//
//            P p = puzzle.initiallPosition();
//            exec.execute(newTask(p,null,null));
//            Node<P,M> node = solution.getValue();
//            return node==null?null:node.asMoveList();
//        }finally {
//
//            exec.shutdown();
//        }
//    }
//
//    protected Runnable newTask(P p, M m, Node<P,M> n){
//        return new SolveTask(p,m,n);
//    }
//
//    class SolveTask extends Node<P,M> implements Runnable{
//        private Node<P,M> node;
//
//        public SolveTask(P pos, M move, Node<P, M> node) {
//            super(pos,move,node);
//            this.node = new Node<>(pos,move,node);
//        }
//
//        @Override
//        public void run() {
//            if(solution.isSet() || seen.putIfAbsent(pos,true)!=null){
//                return;
//            }
//            if(puzzle.isGoal(pos)){
//                solution.setValue(this);
//            }else {
//                for(M m:puzzle.leaglMoves(pos)){
//                    exec.execute(newTask(puzzle.move(pos,m)),m,this);
//                }
//            }
//        }
//    }
//}
