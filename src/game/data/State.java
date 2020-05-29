package game.data;

import java.util.Arrays;

public class State {

    public static int
            EMPTY = 0,
            PAWNW = 1,
            KNIGHTW = 2,
            BISHOPW = 3,
            ROOKW = 4,
            QUEENW = 5,
            KINGW = 6,
            PAWNB = 7,
            KNIGHTB = 8,
            BISHOPB = 9,
            ROOKB = 10,
            QUEENB = 11,
            KINGB = 12;

    public boolean turn = true;
    public boolean check = false;
    public boolean mate = false;
    public boolean draw = false;

    public RC[] pieces;
    public int[][] board;

    public State() {
        board = new int[8][8];
        pieces = new RC[12];
    }

    public State copy() {
        State state = new State();
        state.turn = turn;
        state.check = turn;
        state.mate = turn;
        state.draw = turn;
        state.pieces = Arrays.copyOf(pieces, pieces.length);
        state.board = new int[8][8];
        for(int i = 0; i < 8; ++i) state.board[i] = Arrays.copyOf(board[i], 8);
        return state;
    }

    public State move(RC s, RC e) {
        State state = copy();
        int sid = state.board[s.r][s.c];
        int eid = state.board[e.r][e.c];
        state.board[s.r][s.c] = 0;
        state.board[e.r][e.c] = sid;
        state.pieces[sid] = e;
        state.pieces[eid] = null;
        return state;
    }

}
