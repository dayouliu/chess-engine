package game.ai;

import game.data.State;
import game.logic.Util;

public class Heuristic {

    private double[] material = {1, 3, 3, 5, 9, 200};
    private double mobility = 0.1;

    public double heuristic(State state) {
        double h = 0;
        int[] pieces = Util.pieces(state.board);
        int s = !state.turn ? 1 : 7;
        int e = !state.turn ? 6 : 12;

        for(int i = s; i <= e; ++i) {
            h += pieces[i] * material[i-s];
        }

        for(int i = 0; i < 8; ++i) {
            for(int j = 0; j < 8; ++j) {
                h += state.attack[0][i][j] * mobility;
            }
        }

        // if(state.mate)

        return h;
    }

}
