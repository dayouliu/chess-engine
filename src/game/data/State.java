package game.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public List<List<RCM>> moves = new ArrayList<List<RCM>>();
    public int[][] moved = new int[8][8];
    public int[][] attack = new int[8][8];

    public int[][] board = {{10, 8, 9, 11, 12, 9, 8, 10},
                            {7, 7, 7, 7, 7, 7, 7, 7},
                            {0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0},
                            {1, 1, 1, 1, 1, 1, 1, 1},
                            {4, 2, 3, 5, 6, 3, 2, 4}};

    public RC find(int id) {
        for(int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if(board[i][j] == id) return new RC(i, j);
            }
        }
        return null;
    }

    public RCM last() {
        return moves.isEmpty() ? null : moves.get(moves.size()-1).get(0);
    }

    public void movei(RCM m) {
        int sid = board[s.r][s.c];
        board[s.r][s.c] = 0;
        board[e.r][e.c] = sid;
    }

    public void move(RC s, RC e) {
        int id = board[s.r][s.c];
        board[s.r][s.c] = 0;
        board[e.r][e.c] = id;
        turn = !turn;
        moves.add(new RCM(s, e));
        moved[s.r][s.c] = true;
        moved[e.r][e.c] = true;
    }

}
