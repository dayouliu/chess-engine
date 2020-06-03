package game.gen;

import game.data.RC;
import game.data.State;

public class Move {

    private Validate validate;
    private Attack attack;

    public Move(Attack attack, Validate validate) {
        this.validate = validate;
        this.attack = attack;
    }

    public void move(State state, RC s, RC e) {
        int dr = Util.dr(s, e);
        int dc = Util.dc(s, e);

        // en passant
        if(validate.validatePawnEnpassant(state.board, state.last(), s, e)) {
            state.board[e.r-dr][e.c] = 0;
        }

        // castle
        if(validate.validateKingCastle(state.board, state.attack, state.moved, state.check, s, e)) {
            state.board[e.r-dr][e.c] = 0;
        }

        // move
        state.move(s, e);

        // check
        attack.genAttackArr(state, state.turn);
        int kid = state.turn ? State.KINGW : State.KINGB;
        RC k = state.find(kid);
        if(state.attack[k.r][k.c] > 0) state.check = true; else state.check = false;
        System.out.println("check: " + state.check);

        System.out.println();
    }
    
}
