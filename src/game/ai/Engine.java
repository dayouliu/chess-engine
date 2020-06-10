package game.ai;

import game.data.RCM;
import game.data.ChessState;
import game.logic.Flag;
import game.logic.Gen;
import game.logic.Move2;
import game.logic.Util;

import java.util.ArrayList;
import java.util.List;

public class Engine {

    private Heuristic heuristic;
    private Move2 move2;
    private Gen gen;

    private int NEGINF = Integer.MIN_VALUE;
    private int POSINF = Integer.MAX_VALUE;

    public List<EngineThread> threads = new ArrayList<>();

    public Engine(Heuristic heuristic, Gen gen, Move2 move2) {
        this.heuristic = heuristic;
        this.move2 = move2;
        this.gen = gen;
    }

    public List<RCM> move(ChessState state) {
        List<List<RCM>> next = Util.next(state);
        int index = 0;
        double best = state.turn ? NEGINF : POSINF;
        long calc = 0;
        boolean alldone = true;

        for(int i = 0; i < threads.size(); ++i) {
            EngineThread thread = threads.get(i);
            alldone = alldone && thread.done;
            if(thread.done) {
                if((state.turn && thread.h > best) || (!state.turn && thread.h < best)) {
                    best = thread.h;
                    index = i;
                }
                calc += thread.calc;
            }
        }
        if(alldone) System.out.println("total positions evaluated: " + calc);
        return alldone ? next.get(index) : null;
    }

    public void calc(ChessState state) {
        boolean turn = state.turn;
        threads.clear();
        List<List<RCM>> next = Util.next(state);
        for(int i = 0; i < next.size(); ++i) {
            List<RCM> n = next.get(i);
            move2.move(state, n);
            EngineThread thread = new EngineThread(heuristic, move2, state, 1, NEGINF, POSINF, !turn);
            move2.unmove(state);
            thread.start();
            threads.add(thread);
        }
    }

    /*
    private double minimax(ChessState state, int depth, double alpha, double beta, boolean maximizer) {
        if(depth == maxdepth || state.mate || state.draw) {
            return heuristic.heuristic(state);
        }
        if(maximizer) {
            double max = NEGINF;
            List<List<RCM>> next = Util.next(state);
            for(List<RCM> n : next) {
                move2.move(state, n);
                double eval = minimax(state, depth+1, alpha, beta, false);
                move2.unmove(state);
                max = Math.max(max, eval);
                alpha = Math.max(alpha, eval);
                if(alpha >= beta) break;
            }
            return max;
        } else {
            double min = POSINF;
            List<List<RCM>> next = Util.next(state);
            for(List<RCM> n : next) {
                move2.move(state, n);
                double eval = minimax(state, depth+1, alpha, beta, true);
                move2.unmove(state);
                min = Math.min(min, eval);
                beta = Math.min(beta, eval);
                if(alpha >= beta) break;
            }
            return min;
        }
    }
     */

}
