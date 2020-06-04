package game.gen;

import game.data.RC;
import game.data.RCM;
import game.data.State;

import java.util.List;

public class Move {

    private Attack attack;

    public Move(Attack attack) {
        this.attack = attack;
    }

    public void check(State state) {
        attack.genAttackArr(state);
        RC k = state.find(state.turn ? State.KINGW : State.KINGB);
        state.check = state.attack[k.r][k.c] > 0;
    }

    public void move(State state, List<RCM> move, boolean turn) {
        // turn
        state.turn = turn;

        // move
        for(RCM m : move) {
            state.board[m.s.r][m.s.c] = 0;
            state.board[m.e.r][m.e.c] = m.a;
        }

        // add to move history
        state.moves.add(move);

        // moved
        for(RCM m : move) {
            ++state.moved[m.s.r][m.s.c];
            ++state.moved[m.e.r][m.e.c];
        }

        // check
        check(state);
    }

    public void unmove(State state, boolean turn) {
        // unturn
        state.turn = turn;

        // unmove
        List<RCM> move = state.moves.get(state.moves.size()-1);
        for(RCM m : move) {
            state.board[m.s.r][m.s.c] = m.a;
            state.board[m.e.r][m.e.c] = m.b;
        }

        // add to move history
        state.moves.remove(state.moves.size()-1);

        // unmoved
        for(RCM m : move) {
            --state.moved[m.s.r][m.s.c];
            --state.moved[m.e.r][m.e.c];
        }

        // check
        check(state);
    }

}
