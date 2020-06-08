package game.ai;

import game.data.ChessState;
import game.data.RCM;
import game.logic.Move2;
import game.logic.Util;

import java.util.List;

class EngineThread extends Thread {

    private Heuristic heuristic;
    private Move2 move2;

    private int maxdepth = 3;
    private int NEGINF = Integer.MIN_VALUE;
    private int POSINF = Integer.MAX_VALUE;

    private ChessState state;
    private int depth;
    private double alpha, beta;
    private boolean maximizer;

    public double h;
    public long calc = 0;
    public boolean done = false;

    public EngineThread(Heuristic heuristic, Move2 move2, ChessState state, int depth, double alpha, double beta, boolean maximizer) {
        this.heuristic = heuristic;
        this.move2 = move2;
        this.state = state.copy();
        this.depth = depth;
        this.alpha = alpha;
        this.beta = beta;
        this.maximizer = maximizer;
    }

    private double minimax(ChessState state, int depth, double alpha, double beta, boolean maximizer) {
        ++calc;
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

    public void run() {
        h = minimax(state, depth, alpha, beta, maximizer);
        done = true;
    }

}
