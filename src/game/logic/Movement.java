package game.logic;

import game.data.RC;
import game.data.RCM;
import game.data.State;

import java.util.ArrayList;
import java.util.List;

public class Movement {

    private Attack attack;

    public Movement(Attack attack) {
        this.attack = attack;
    }

    public void move(State state, List<RCM> move) {
        // move
        for(RCM m : move) {
            state.board[m.s.r][m.s.c] = 0;
            state.board[m.e.r][m.e.c] = m.a;
        }

        // add to move history
        state.moves.add(new ArrayList<RCM>(move));

        // moved
        for(RCM m : move) {
            ++state.moved[m.s.r][m.s.c];
            ++state.moved[m.e.r][m.e.c];
        }

        attack.genAttackArr(state);
    }

    public void unmove(State state) {
        // unmove
        List<RCM> move = state.moves.get(state.moves.size()-1);
        for(int i = move.size()-1; i >= 0; --i) {
            RCM m = move.get(i);
            state.board[m.s.r][m.s.c] = m.a;
            state.board[m.e.r][m.e.c] = m.b;
        }

        // remove from move history
        state.moves.remove(state.moves.size()-1);

        // unmoved
        for(RCM m : move) {
            --state.moved[m.s.r][m.s.c];
            --state.moved[m.e.r][m.e.c];
        }

        attack.genAttackArr(state);
    }

}
