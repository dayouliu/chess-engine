package game.ai;

import game.data.ChessState;
import game.logic.Util;

public class Heuristic {

    private double[] material = {1, 3, 3, 5, 9, 200};

    public double heuristic(ChessState state) {
        double w = 0;
        double b = 0;

        int[] pieces = Util.pieces(state.board);
        for(int i = 1; i <= 6; ++i)  w += pieces[i] * material[i-1];
        for(int i = 7; i <= 12; ++i) b += pieces[i] * material[i-7];

        for(int i = 0; i < 8; ++i) {
            for(int j = 0; j < 8; ++j) {
                double mobility = 0.05 + 0.05 * ((Math.abs(i-3.5)+Math.abs(j-3.5)) / 7);
                w += state.attacked[0][i][j] * mobility;
                b += state.attacked[1][i][j] * mobility;
            }
        }

        if(state.mate && !state.turn) return 200;
        if(state.mate && state.turn) return -200;
        if(state.draw) return 0;

        return w-b;
    }

}
