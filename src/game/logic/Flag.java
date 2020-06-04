package game.logic;

import game.data.RC;
import game.data.RCM;
import game.data.State;

import java.util.Arrays;
import java.util.List;

public class Flag {

    private Gen gen;

    public Flag(Gen gen) {
        this.gen = gen;
    }

    private int[][] draws = {
            {2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
            {3, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
            {3, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1},
            {3, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1},
            {3, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1},
    };

    public void turn(State state) {
        state.turn = !state.turn;
    }

    public void check(State state) {
        RC k = state.find(state.turn ? State.KINGW : State.KINGB);
        state.check = state.attack[state.turn ? 1 : 0][k.r][k.c] > 0;
    }

    public void mate(State state) {
        state.mate = state.next.isEmpty();
    }

    public void draw(State state) {
        int[] pieces = Util.pieces(state.board);
        for(int[] pos : draws) {
            if(Arrays.equals(pieces, pos)) {
                state.draw = true;
                break;
            }
        }
    }

    public void flag(State state) {
        turn(state);
        check(state);
        state.next = gen.genMoves(state, state.turn);
        mate(state);
        draw(state);
    }

}
