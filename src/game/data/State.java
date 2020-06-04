package game.data;

import game.logic.Util;

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

    public int[][] moved = new int[8][8];
    public int[][][] attack = new int[2][8][8];
    public List<List<RCM>> moves = new ArrayList<List<RCM>>();
    public List<List<RCM>> next;

    /*
    public int[][] board = {{10, 0, 0, 0, 0, 10, 12, 0},
                            {0, 7, 7, 0, 0, 7, 7, 7},
                            {0, 0, 8, 7, 11, 0, 0, 0},
                            {7, 0, 9, 0, 0, 0, 0, 0},
                            {1, 0, 1, 5, 1, 0, 0, 0},
                            {0, 0, 7, 0, 0, 0, 1, 0},
                            {0, 1, 0, 0, 0, 0, 3, 0},
                            {4, 0, 3, 0, 0, 4, 6, 0}};
    */

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

}
