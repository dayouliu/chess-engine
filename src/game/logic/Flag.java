package game.logic;

import game.data.RCM;
import game.data.ChessState;

import java.util.Arrays;
import java.util.List;

public class Flag {

    private int[][] draws = {
            {2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
            {3, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
            {3, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1},
            {3, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1},
            {3, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1},
    };

    public void turn(ChessState state) {
        state.turn = !state.turn;
    }

    public void mate(ChessState state, List<List<RCM>> next) {
        state.mate = next.isEmpty();
    }

    public void draw(ChessState state) {
        int[] pieces = Util.pieces(state.board);
        for(int[] pos : draws) {
            if(Arrays.equals(pieces, pos)) {
                state.draw = true;
                break;
            }
        }
    }

    public void unflag(ChessState state) {
        turn(state);
        state.mate = false;
        state.draw = false;
    }

}
