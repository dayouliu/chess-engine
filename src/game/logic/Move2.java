package game.logic;

import game.data.RCM;
import game.data.ChessState;

import java.util.List;

public class Move2 {

    private Move1 move1;
    private Validate validate;
    private Gen gen;
    private Flag flag;

    public Move2(Move1 move1, Validate validate, Gen gen, Flag flag) {
        this.move1 = move1;
        this.validate = validate;
        this.gen = gen;
        this.flag = flag;
    }

    public void move(ChessState state, List<RCM> m) {
        move1.move(state, m);
        flag.turn(state);
        flag.draw(state);
        List<List<RCM>> next = gen.genMoves(state, state.turn);
        state.nexts.add(next);
        flag.mate(state, next);
    }

    public void unmove(ChessState state) {
        move1.unmove(state);
        state.nexts.remove(state.nexts.size()-1);
        flag.unflag(state);
    }

}
