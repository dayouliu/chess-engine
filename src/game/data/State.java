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
        int[][] b = {{10, 8, 9, 11, 12, 9, 8, 10},
                     {7, 7, 7, 7, 7, 7, 7, 7},
                     {0, 0, 0, 0, 0, 0, 0, 0},
                     {0, 0, 0, 0, 0, 0, 0, 0},
                     {0, 0, 0, 0, 0, 0, 0, 0},
                     {0, 0, 0, 0, 0, 0, 0, 0},
                     {1, 1, 1, 1, 1, 1, 1, 1},
                     {4, 2, 3, 5, 6, 3, 2, 4}};
        board = b;
        pieces = new RC[13];
        for(int i = 0; i < 8; ++i) {
            for(int j = 0; j < 8; ++j) {
                int id = board[i][j];
                if(id > 0) pieces[id] = new RC(i, j);
            }
        }
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

    public void move(RC s, RC e) {
        int sid = board[s.r][s.c];
        int eid = board[e.r][e.c];
        board[s.r][s.c] = 0;
        board[e.r][e.c] = sid;
        pieces[sid] = e;
        pieces[eid] = null;
    }

}
